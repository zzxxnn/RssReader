package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.bind.ParseConversionEvent;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import dao.NewsDao;
import dao.impl.FileDaoImpl;
import model.Channel;
import model.News;

public class RSSService {
	List<Channel> channels = null;
	private List<News> newslist = null;

	public RSSService() {
		
	}

	public List<Channel> getChannelList() {
		if (channels == null) {
			channels = new ArrayList<Channel>();
			Channel channel = new Channel();
			channel.setName("腾讯-国际新闻");
			channel.setFilePath("NewFiles/rss_newswj.xml");
//			channel.setUrl("http://news.qq.com/newsgj/rss_newswj.xml");
			channel.setUrl("http://rss.sina.com.cn/news/society/focus15.xml");

			Channel channel2 = new Channel();
			channel2.setName("腾讯-国内新闻");
			channel2.setFilePath("NewFiles/rss_newsgn.xml");
//			channel2.setUrl("http://news.qq.com/newsgn/rss_newsgn.xml");
			channel2.setUrl("http://rss.sina.com.cn/news/society/focus15.xml");

			Channel channel3 = new Channel();
			channel3.setName("新浪-体育新闻");
			channel3.setFilePath("NewFiles/sports.xml");
//			channel3.setUrl("http://rss.sina.com.cn/news/allnews/sports.xml");
			channel3.setUrl("http://rss.sina.com.cn/news/society/focus15.xml");

			Channel channel4 = new Channel();
			channel4.setName("新浪-社会新闻");
			channel4.setFilePath("NewFiles/focus15.xml");
			channel4.setUrl("http://rss.sina.com.cn/news/society/focus15.xml");
			
			channels.add(channel);
			channels.add(channel4);
			channels.add(channel3);
			channels.add(channel2);

		}
		return channels;
	}

	public Document load(String filePath) {
		Document document = null;
		// 指定解析器
		SAXBuilder sb = new SAXBuilder(false);
		// 创建文件对象
		File fxml = new File(filePath);
		if (fxml.exists() && fxml.isFile()) {
			try {
				document = sb.build(fxml);
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return document;
	}

	public List<News> getNewsList(String filepath) {
		Document document = load(filepath);
		newslist = Parse(document);
		return newslist;
	}

	private List<News> Parse(Document document) {
		newslist = new ArrayList<News>();
		News news = null;
		Element root = document.getRootElement();
		Element eChannel = root.getChild("channel");
		List<Element> itemlist = eChannel.getChildren("item");
		for (int i = 0; i < itemlist.size(); i++) {
			news = itemToNews(itemlist.get(i));
			newslist.add(news);
		}
		// for (Element element : itemlist) {
		// news = itemToNews(element);
		// newslist.add(news);
		// }
		return newslist;
	}

	private News itemToNews(Element element) {
		News news = new News();
		String title = element.getChildText("title").trim();
		String link = element.getChildText("link");
		String author = element.getChildText("author");
		String guid = element.getChildText("guid");
		String category = element.getChildText("category");
		String pubDate = element.getChildText("pubDate");
		String description = element.getChildText("description").trim();
		news.setAuthor(author);
		news.setCategory(category);
		news.setDescription(description);
		news.setGuid(guid);
		news.setLink(link);
		news.setPubDate(pubDate);
		news.setTitle(title);
		return news;
	}

	public String newsToString(News news) {
		return "标题" + news.getTitle() + "\r\n" + "链接" + news.getLink() + "\r\n"
				+ "作者" + news.getAuthor() + "\r\n" + "发布时间" + news.getPubDate()
				+ "\r\n" + "---------------------------------\r\n" + "描述"
				+ news.getDescription() + "\r\n";
	}

	/**
	 * 保存新闻
	 */
	public boolean save() {
		NewsDao dao = new FileDaoImpl();
		if (newslist == null) {
			JOptionPane.showMessageDialog(null, "请先选择新闻");
			return false;
		}
		return dao.save(newslist);

	}

}
