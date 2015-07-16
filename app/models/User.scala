package models

/**
 * Created by afynn on 7/7/15.
 */


case class User(
                 email: String,
                 firstName: String,
                 lastName: String,
                 braintreeProfile: BraintreeProfile
                 )


case class UserLogin(
                      email: String,
                      password: Option[String]
                      )


case class UserRegistration(
                             email: String,
                             firstName: String,
                             lastName: String,
                             userType: String,
                             project: String,
                             password: Option[String]
                             )
