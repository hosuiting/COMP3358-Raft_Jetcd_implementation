package COMP3358Assignment5;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Charsets.UTF_8;

import java.util.concurrent.ExecutionException;

public class jetcdAPI {
	//private static final Logger LOGGER = LoggerFactory.getLogger(jetcdAPI.class);
	Logger logger = Logger.getGlobal();
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
	
    public String get(String key) throws ExecutionException, InterruptedException{
        //log.info("start get, key [{}]", key);
        GetResponse response = getKVClient().get(bytesOf(key)).get();

        if (response.getKvs().isEmpty()) {
            //log.error("empty value of key [{}]", key);
            return null;
        }

        String value = response.getKvs().get(0).getValue().toString(UTF_8);
        //log.info("finish get, key [{}], value [{}]", key, value);
        logger.info("hi");
        return value;
    }
    public PutResponse put(String key, String value) throws ExecutionException, InterruptedException {
        //log.info("start put, key [{}], value [{}]", key, value);
        return getKVClient().put(bytesOf(key), bytesOf(value)).get();
    }
    
    public String incGet(String key) throws ExecutionException, InterruptedException {
        GetResponse response = getKVClient().get(bytesOf(key)).get();
        if (response.getKvs().isEmpty()) {
            return null;
        }
        String value = response.getKvs().get(0).getValue().toString(UTF_8); 
        int temValue = Integer.parseInt(value);
        temValue ++;
        value = Integer.toString(temValue);
        
        PutResponse putresponse = getKVClient().put(bytesOf(key), bytesOf(value)).get();
        if (putresponse ==null) {
        	return null;
        }
        
        GetResponse response2 = getKVClient().get(bytesOf(key)).get();
        if (response2.getKvs().isEmpty()) {
            return null;
        }
        String final_value = response2.getKvs().get(0).getValue().toString(UTF_8);
        return final_value;
    }
    
    public String decGet(String key) throws ExecutionException, InterruptedException {
        GetResponse response = getKVClient().get(bytesOf(key)).get();
        if (response.getKvs().isEmpty()) {
            return null;
        }
        String value = response.getKvs().get(0).getValue().toString(UTF_8); 
        //System.out.println("Original value: " + value);
        int temValue = Integer.parseInt(value);
        temValue --;
        value = Integer.toString(temValue);
        
        PutResponse putresponse = getKVClient().put(bytesOf(key), bytesOf(value)).get();
        if (putresponse ==null) {
        	return null;
        }
        
        GetResponse response2 = getKVClient().get(bytesOf(key)).get();
        if (response2.getKvs().isEmpty()) {
            return null;
        }
        String final_value = response2.getKvs().get(0).getValue().toString(UTF_8);
        //System.out.println("Final value: " + value);
        return final_value;
    }
}
