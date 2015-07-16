
import com.braintreegateway.*;
import models.BraintreeProfile;
import org.junit.Test;
import scala.collection.mutable.Set;
import services.BraintreeService;

import java.math.BigDecimal;

/**
 * Created by afynn on 7/8/15.
 */
public class CustomerTest {

    @Test
    public void testGateway(){

        BraintreeGateway gateway = BraintreeService.getGateway();

        System.out.println("gateway - " + gateway.merchantAccount().toString());
    }

    @Test
    public void testGetCustomerByCompany(){
        Set<BraintreeProfile> customers = BraintreeService.findByCompany("test1@advanc-ed.org");

        System.out.println("customers - " + customers);

        /*for (BraintreeProfile customer :customers) {
            System.out.println(customer.firstName() + " " + customer.lastName() + " - " + customer.id());
        }*/
    }

    //@Test
    public void testCreateMerchantAccountUSA(){

        BraintreeGateway gateway = BraintreeService.getGateway();

        MerchantAccountRequest request = new MerchantAccountRequest().
                individual().
                    firstName("Advance").
                    lastName("USA").
                    email("usa@advanc-ed.org").
                    phone("5553334444").
                    dateOfBirth("1981-11-19").
                    //ssn("456-45-4567").
                    address().
                        streetAddress("9115 Westside Pkwy").
                        locality("Alpharetta").
                        region("GA").
                        postalCode("30009").
                        done().
                    done().
                business().
                    legalName("Advanced USA").
                    dbaName("Advanced USA").
                    taxId("98-7654321").
                    address().
                        streetAddress("9115 Westside Pkwy").
                        locality("Alpharetta").
                        region("GA").
                        postalCode("30009").
                        done().
                done().
                funding().
                    descriptor("advanced usa funding").
                    destination(MerchantAccount.FundingDestination.BANK).
                    email("funding@wellsfargo.com").
                    mobilePhone("7071101307").
                    accountNumber("1123581321").
                    routingNumber("071101307").
                    done().
                tosAccepted(true).
                masterMerchantAccountId("advanced").
                id(BraintreeService.ADVANCED_USA_MERCHANT_ID);

        Result<MerchantAccount> result = gateway.merchantAccount().create(request);

        BraintreeService.printResult(result);

    }

    @Test
    public void testCreateMerchantAccountNetherlands(){

        BraintreeGateway gateway = BraintreeService.getGateway();

        MerchantAccountRequest request = new MerchantAccountRequest().
                individual().
                    firstName("Advance").
                    lastName("Netherlands").
                    email("netherlands@advanc-ed.org").
                    phone("5553334444").
                    dateOfBirth("1981-11-19").
                    //ssn("456-45-4567").
                    address().
                        streetAddress("9115 Westside Pkwy").
                        locality("Alpharetta").
                        region("GA").
                        postalCode("30009").
                        done().
                    done().
                business().
                    legalName("Advanced Netherlands").
                    dbaName("Advanced NE").
                    taxId("98-7654321").
                    address().
                        streetAddress("9115 Westside Pkwy").
                        locality("Alpharetta").
                        region("GA").
                        postalCode("30009").
                        done().
                    done().
                funding().
                    descriptor("advanced netherlands funding").
                    destination(MerchantAccount.FundingDestination.BANK).
                    email("Netherlands@wellsfargo.com").
                    mobilePhone("7071101307").
                    accountNumber("1123581321").
                    routingNumber("071101307").
                done().
                tosAccepted(true).
                masterMerchantAccountId("advanced").
                id(BraintreeService.ADVANCED_NETHERLANDS_MERCHANT_ID);

        Result<MerchantAccount> result = gateway.merchantAccount().create(request);

        BraintreeService.printResult(result);
    }


    @Test
    public void testPay(){
        Result<Transaction> result = BraintreeService.checkout(
                BraintreeService.UNITED_STATES,
                "c497485f-fade-4080-982f-b28f1c83a21d",
                BigDecimal.valueOf(15.0),
                null,
                null);

        BraintreeService.printResult(result);
    }


}
