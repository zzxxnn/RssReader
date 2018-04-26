package view;

import static org.junit.Assert.*;

import org.junit.Test;

import service.UpdateThread;

public class test {

	@Test
	public void test() throws Exception {
		new UpdateThread().upDate("http://news.qq.com/newsgn/rss_newsgn.xml", "NewFiles/rss_newsgn.xml");
	}

}
