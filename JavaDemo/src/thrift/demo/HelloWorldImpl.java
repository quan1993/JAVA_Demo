package thrift.demo;

import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorldService.Iface {

	public HelloWorldImpl() {
	}

	@Override
	public String sayHello(String username) throws TException {
		System.out.println("i am server, receive     " + username);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Hi," + username + " welcome to my blog www.micmiu.com";
	}

}

