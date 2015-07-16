package services;

import com.braintreegateway.*;
import com.braintreegateway.Result;
import models.BraintreeProfile;
import models.Profiles;
import org.apache.commons.lang3.StringUtils;
import scala.collection.mutable.Set;
import scala.collection.mutable.HashSet;

import java.math.BigDecimal;

/**
 * Created by afynn on 7/7/15.
 */
public class BraintreeService {

    public static final String UNITED_STATES = "United States";
    public static final String NETHERLANDS = "Netherlands";

    private static final BraintreeGateway US_GATEWAY = new BraintreeGateway(
            Environment.SANDBOX,
            "cy92z786sd46htbq",
            "yd93xzm5kd4pwtgs",
            "4276fcf4106772755af045ba40251114"
    );

    public static final String ADVANCED_USA_MERCHANT_ID = "USA";
    public static final String ADVANCED_NETHERLANDS_MERCHANT_ID = "Netherlands";


    public static final String CUSTOMER_NUMBER_API_NAME = "cnumber";
    public static final String PRODUCT_API_NAME = "product";


    public static BraintreeGateway getGateway(){
        return US_GATEWAY;
    }

    public static String getMerchantID(final String country){
        if(StringUtils.equalsIgnoreCase(UNITED_STATES, country)){
            return ADVANCED_USA_MERCHANT_ID;
        }
        else if(StringUtils.equalsIgnoreCase(NETHERLANDS, country)){
            return ADVANCED_NETHERLANDS_MERCHANT_ID;
        }
        return null;
    }

    public static String getToken() {
        return getGateway().clientToken().generate();
    }


    public static Result<Transaction> checkout(
            final String country, final String nonce, final BigDecimal amount,
            final String product, final BraintreeProfile braintreeProfile) {

        TransactionRequest request = new TransactionRequest()
                .amount(amount)
                .paymentMethodNonce(nonce)
                .merchantAccountId(getMerchantID(country))
                .customer()
                    .firstName(braintreeProfile.firstName())
                    .lastName(braintreeProfile.lastName())
                    .done()
                .customField(CUSTOMER_NUMBER_API_NAME, braintreeProfile.customFields().customerNumber())
                .customField(PRODUCT_API_NAME, product)
                ;

        Result<Transaction> result = getGateway().transaction().sale(request);

        return result;
    }

    public static Result<Customer> registerAtBraintree (final BraintreeProfile braintreeProfile) {

        CustomerRequest request = new CustomerRequest()
                .firstName(braintreeProfile.firstName())
                .lastName(braintreeProfile.lastName())
                .company(braintreeProfile.company())
                .email(braintreeProfile.email())
                .fax(braintreeProfile.fax())
                .phone(braintreeProfile.phone())
                .website(braintreeProfile.website());


        Result<Customer> result = getGateway().customer().create(request);

        return result;
    }

    public static Set<BraintreeProfile> findByCompany (final String company) {
        CustomerSearchRequest request = new CustomerSearchRequest()
                .company().is(company);

        ResourceCollection<Customer> collection = getGateway().customer().search(request);

        return getProfiles(collection);
    }

    public static Set<BraintreeProfile> getProfiles(ResourceCollection<Customer> collection){

        Set<BraintreeProfile> profileSet = new HashSet<>();
        for(Customer customer : collection){
            profileSet.add(Profiles.getBraintreeProfile(customer));
        }

        return profileSet;
    }


    public static void printResult(final Result result){

        play.Logger.info("result - " + result.isSuccess());
        if(result.getMessage() != null) {
            play.Logger.info("message - " + result.getMessage());
        }
        if(result.getErrors() != null){
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                play.Logger.error(error.getMessage());
            }
        }
    }
}
