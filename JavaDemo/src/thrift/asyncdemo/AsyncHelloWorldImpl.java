package thrift.asyncdemo;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

import thrift.demo.HelloWorldService;

public class AsyncHelloWorldImpl  implements HelloWorldService.AsyncIface {
	
	public AsyncHelloWorldImpl() {
	}
	
	@Override
	public void sayHello(String username, AsyncMethodCallback<String> resultHandler) throws TException {
		System.out.println("async hello world impl    say hello");
		
	}
	
}
