package dao;

import java.util.List;

import model.News;

public interface NewsDao {
	public boolean save(List<News> newsList);
}
