package models

import com.braintreegateway.{Customer, Transaction, Result}
import play.api.libs.json.{Json, Writes}
import play.api.data.validation.ValidationError

/**
 * Created by afynn on 7/9/15.
 */

object JsonFormats {

  import play.api.libs.json._

  import play.api.libs.functional.syntax._


  implicit val customerRegistrationReads: Reads[CustomerRegistration] = (
    (JsPath \ "email").read[String] and
      (JsPath \ "firstName").read[String] and
      (JsPath \ "lastName").read[String] and
      (JsPath \ "phone").readNullable[String]
    )(CustomerRegistration.apply _)


  /*implicit val creditCardWrites = new Writes[CreditCard] {
    def writes(card: CreditCard) = Json.obj(
      "address" -> card.billingAddress,
      "name" -> card.cardHolderName
    )
  }*/

  implicit val profileWrites = new Writes[BraintreeProfile] {
    def writes(profile: BraintreeProfile) = Json.obj(
      "company" -> profile.company,
      //"creditCard" -> profile.creditCard,
      "email" -> profile.email,
      "firstName" -> profile.firstName,
      "lastName" -> profile.lastName,
      "phone" -> profile.phone
    )
  }

  def getSeq(profiles: scala.collection.mutable.Set[BraintreeProfile]) =
    for{
      user <- profiles
    } yield user

  def getJson(profiles: scala.collection.mutable.Set[BraintreeProfile]) = Json.toJson(getSeq(profiles))



  implicit val validationErrorWrites = new Writes[ValidationError] {
    def writes(error: ValidationError) = Json.obj(
      "messages" -> error.message
    )
  }

  def getErrorsJson(errors: Seq[ValidationError]) = Json.toJson(errors)



  implicit val braintreeTransactionResultWrites = new Writes[Result[Transaction]] {
    def writes(result: Result[Transaction]) = Json.obj(
      "success" -> result.isSuccess,
      "message" -> result.getMessage
    )
  }
  def getTransactionResultJson(result: Result[Transaction]) = Json.toJson(result)


  implicit val braintreeCustomerResultWrites = new Writes[Result[Customer]] {
    def writes(result: Result[Customer]) = Json.obj(
      "success" -> result.isSuccess,
      "message" -> result.getMessage
    )
  }
  def getCustomerResultJson(result: Result[Customer]) = Json.toJson(result)
}
