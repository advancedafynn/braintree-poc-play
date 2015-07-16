package controllers

import controllers.Customers._
import models.{JsonFormats, CustomFields, BraintreeProfile}
import play.api.mvc.Action
import services.BraintreeService
import views.html

/**
 * Created by afynn on 7/7/15.
 */
object Checkout {


  def index = Action {
    Ok(html.customer.checkout());
  }

  def getToken () = Action {
    Ok(BraintreeService.getToken());
  }

  def pay = Action { implicit request =>

    val jsonValues = request.body.asJson.get;

    val jsNonce = jsonValues\"nonce"
    val jsAmount = jsonValues\"amount"
    val jsCountry = jsonValues\"country"
    val jsCustomer = jsonValues\"customer"
    val jsProduct = jsonValues\"product"

    val amount = new java.math.BigDecimal(jsAmount.as[String])

    //play.Logger.info("nonce - " + jsNonce);
    //play.Logger.info("customer - " + jsCustomer);

    val jsFirstName = jsCustomer\"firstName"
    val jsLastName = jsCustomer\"lastName"
    val jsCustomerNumber = jsCustomer\"number"

    val profile = BraintreeProfile(
      null,null,CustomFields(jsCustomerNumber.as[String]),
      null,null,null,
      null,jsFirstName.as[String],jsLastName.as[String],
      null,null, null);

    val result = BraintreeService.checkout(
      jsCountry.as[String],
      jsNonce.as[String],
      amount,
      jsProduct.as[String],
      profile);

    BraintreeService.printResult(result)

    Ok(JsonFormats.getTransactionResultJson(result))

  }
}
