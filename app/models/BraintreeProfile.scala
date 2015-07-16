package models

import com.braintreegateway.Customer

/**
 * Created by afynn on 7/7/15.
 */
case class BraintreeProfile(
                           company: String,
                           creditCredit:  CreditCard,
                           customFields: CustomFields,
                           deviceData: String,
                           email: String,
                           id: String,
                           fax: String,
                           firstName: String,
                           lastName: String,
                           paymentMethodNonce: String,
                           phone: String,
                           website: String
                             )


case class CustomFields(
                         customerNumber: String
                         )


case class CreditCard(
                     billingAddress: Address,
                     cardHolderName: String,
                     cvv: String,
                     expirationDate: String,
                     expirationMonth: String,
                     expirationYear: String,
                     number: String,
                     options: CardOptions,
                     token: String
                       )


case class CardOptions(
                      failOnDuplicatePaymentMethods: Boolean,
                      makeDefault: Boolean,
                      verifyCard: Boolean
                           )


case class Address(
                  company: String,
                  countryCodeAlpha2: String,
                  countryCodeAlpha3: String,
                  countryCodeNumeric: String,
                  countryName: String,
                  extendedAddress: String,
                  firstName: String,
                  lastName: String,
                  locality: String,
                  postalCode: String,
                  region: String,
                  streetAddress: String
                    )


case class AddressOptions(
                         )






case class CustomerRegistration(
                                 email: String,
                                 firstName: String,
                                 lastName: String,
                                 phone: Option[String]
                                 )






object Profiles {

  val company = "Advanc-Ed"

  def getBraintreeProfile (customerRegistration: CustomerRegistration) = {
    BraintreeProfile (
      company, //customerRegistration.company,
      null,
    null,
    null,
    customerRegistration.email,
    null,//id
    null,
    customerRegistration.firstName,
    customerRegistration.lastName,
    null,
    customerRegistration.phone.getOrElse(null),
    null

    )
  }



  def getBraintreeProfile (customer: Customer) = {
    BraintreeProfile (
      customer.getCompany,
      null,
      null,
      null,
      customer.getEmail,
      null,//id
      null,
      customer.getFirstName,
      customer.getLastName,
      null,
      customer.getPhone,
      null

    )
  }

}
