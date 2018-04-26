package dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import service.RSSService;
import model.News;
import dao.NewsDao;

public class FileDaoImpl implements NewsDao{
	
	private static String FILE_PATH = "NewFiles/rss.txt";
	
	@Override
	public boolean save(List<News> newsList) {
		//���ļ�
		boolean flag = true;
		File file = new File(FILE_PATH);
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		//д�ļ�
		try {
			fileWriter = new FileWriter(file,true);
			bufferedWriter = new BufferedWriter(fileWriter);
			RSSService rssService = new RSSService();
			for (News news : newsList) {
				String content = rssService.newsToString(news);
				bufferedWriter.write(content);
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		//�ر��ļ�
		return flag;
	}

}
