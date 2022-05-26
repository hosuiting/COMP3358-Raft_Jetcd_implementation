package COMP3358Assignment5;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.base.Charsets.UTF_8;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class tradingAPI {
	private static final Logger LOGGER = LoggerFactory.getLogger(tradingAPI.class);
	
	private KV getKVClient() {
		String endpoints = "http://127.0.0.1:2380,http://127.0.0.1:2381,http://127.0.0.1:2382";
		Client client = Client.builder().endpoints(endpoints.split(",")).build();
		KV kvClient = client.getKVClient();
		return kvClient;
		//return client.getKVClient();
	}
	private static ByteSequence bytesOf(String variable) {
		return ByteSequence.from(variable,UTF_8);
		//return ByteSequence.from(variable.getBytes());
	}
	
	public String CreateAccount(String account)throws ExecutionException, InterruptedException{
    	Date now = new Date();
    	long msSend = now.getTime();
        GetResponse response = getKVClient().get(bytesOf(account)).get();
        if (response.getKvs().isEmpty()) {
            String value = Integer.toString(0); // store 0 into value
            PutResponse putresponse = getKVClient().put(bytesOf(account), bytesOf(value)).get(); //store balance = 0 into account
            //finding the latency
            now = new Date();
            long msReceived = now.getTime();
            long latency= msReceived - msSend;
            LOGGER.info("CreateAccount(*) success, latency: " + latency + "account:" + account );
            return "Success";
        }else {
        	LOGGER.warn("CreateAccount(*) fail, account:" + account );
        	return "Account Exists";
        }
	}
	
	public String SendPayment(String sourceAccount,String destAccount, float amount)throws ExecutionException, InterruptedException{
    	Date now = new Date();
    	long msSend = now.getTime();
    	//Check whether two accounts exist or not
        GetResponse response = getKVClient().get(bytesOf(sourceAccount)).get();
        GetResponse response2 = getKVClient().get(bytesOf(destAccount)).get();
        if (response.getKvs().isEmpty()) {  //sourceAccount not exists 
        	LOGGER.warn("SendPayment(*) fail, sourceAccount:" + sourceAccount);
        	return "Source Account Not Exists";
        }
        if (response2.getKvs().isEmpty()) {  //destAccount not exists 
        	LOGGER.warn("SendPayment(*) fail, destAccount:" + destAccount);
        	return "Destination Account Not Exists";
        }
        String sourceAccountBalance = response.getKvs().get(0).getValue().toString(UTF_8); 
        float sourceBalance = Float.parseFloat(sourceAccountBalance);
        if (sourceBalance>=amount) {
        	//update sourceAccount balance
        	float newBalance = sourceBalance - amount;
        	String value = Float.toString(newBalance);
        	PutResponse putresponse = getKVClient().put(bytesOf(sourceAccountBalance), bytesOf(value)).get();
        	
        	//update destAccount balance
            String destAccountBalance = response.getKvs().get(0).getValue().toString(UTF_8); 
            float destBalance = Float.parseFloat(destAccountBalance);
            destBalance = destBalance + amount;
            String newValue = Float.toString(newBalance);
        	PutResponse putresponse2 = getKVClient().put(bytesOf(destAccountBalance), bytesOf(newValue)).get();
        	
        	//print result      
            now = new Date();
            long msReceived = now.getTime();
            long latency= msReceived - msSend;
        	LOGGER.info("SendPayment(*) success, latency: " + latency + "sourceAccount:" + sourceAccount + " balance: " + sourceBalance);
            return "Success";
        }else {  //not enough money in source Account
        	LOGGER.warn("SendPayment(*) fail, sourceAccount:" + sourceAccount + " balance: " + sourceBalance + " amount: " + amount);
        	return "Source Account Not Enough Money";
        }

	}
}
