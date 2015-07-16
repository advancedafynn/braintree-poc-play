package controllers

import com.braintreegateway.Result
import models.{BraintreeProfile, JsonFormats, Profiles, CustomerRegistration}
import play.api.libs.json.{Json, JsValue, JsPath, Reads}
import play.api.mvc.{Action, Controller}
import services.BraintreeService
import views.html

/**
 * Created by afynn on 7/7/15.
 */
object Customers extends Controller {

  def index = Action {
    Ok(html.customer.customers());
  }

  def add = Action { implicit request =>

    val customerJson : Option[JsValue] = request.body.asJson
    play.Logger.info("customer - " + mapOptions(customerJson));

    JsonFormats.customerRegistrationReads.reads(customerJson.get).fold(
      invalid = {
        fieldErrors =>
          /*fieldErrors.foreach(x => {
            println("field: " + x._1 + ", errors: " + x._2)
            })*/
          val errors = for {
            x <- fieldErrors
          } yield (x._1.toJsonString)

          BadRequest(errors.toString())
      },
      valid = {
        customerRegistration =>
          val success = registerAtBraintree(customerRegistration)
          play.Logger.info("success - " + success);
      }
    )
    Ok;
  }

  def mapOptions(options: Option[JsValue]) =  {
    options map (t => t(0).toString() -> t(1).toString()) toMap
  }


  def registerAtBraintree (customerRegistration: CustomerRegistration) = Boolean2boolean {

    val braintreeProfile = Profiles.getBraintreeProfile(customerRegistration);
    play.Logger.info("braintreeProfile - " + braintreeProfile);

    val result = BraintreeService.registerAtBraintree(braintreeProfile);

    result.isSuccess
  }



  def getByCompany = Action {
    val workers = BraintreeService.findByCompany(Profiles.company)
    Ok(JsonFormats.getJson(workers))
  }

}
