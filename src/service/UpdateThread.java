package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Savepoint;
import java.util.List;

import javax.swing.JOptionPane;

import view.ShowMessage;
import model.Channel;
import model.News;

public class UpdateThread extends Thread{
	
	private final static int DELAY_TIME = 300*1000;
	
//	public static void main(String[] args) throws Exception {
//		RSSService rssService = new RSSService();
//		List<Channel> channels = rssService.getChannelList();
//		Channel channel = channels.get(0);
//		new UpdateThread().upDate(channel.getUrl(), channel.getFilePath());
//	}
	/**
	 * 更新新闻
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public void upDate(String src, String dest) throws Exception {
		URL url = new URL(src);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//判断是否连接成功
		int a = connection.getResponseCode();
		if (a != HttpURLConnection.HTTP_OK) {
			JOptionPane.showMessageDialog(null, "连接失败");
			return;
		}

		File file = new File(dest);
		//判断是否有新内容
		if (hasNewRSS(connection, file)) {
			//下载新内容到buffer
			ByteBuffer buffer = download(connection);
			if (buffer != null) {
				//保存内容
				Save(buffer,file);
			}
			
		}
		
	}
	
	private void Save(ByteBuffer buffer, File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		FileChannel channel = fileOutputStream.getChannel();
		channel.write(buffer);
		channel.close();
	}

	List<Channel> channels ;
	

	@Override
	public void run() {
		while (true) {
			for (int i = 0; i < channels.size(); i++) {
				Channel channel = channels.get(i);
				System.out.println("UpdateThread.run()");
				try {
					upDate(channel.getUrl(), channel.getFilePath());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				sleep(DELAY_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean hasNewRSS(HttpURLConnection connection,File file){
		long current = System.currentTimeMillis();
		long httplastmodified = connection.getHeaderFieldDate("Last-Modified", current);
		long dilelastmodified = file.lastModified();
		if (httplastmodified > dilelastmodified) {
			return true;
		}else {
			return false;
		}
	
	}
	
	private ByteBuffer download(HttpURLConnection connection) throws IOException{
		ByteBuffer buffer = ByteBuffer.allocate(65536);
		
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = "",out= "";
		while ((line = bufferedReader.readLine()) != null) {
			
			out+=line+"\n";
		}
		buffer.put(out.getBytes("utf-8"));
		buffer.flip();
		connection.disconnect();
		return buffer;
		
	}
	public UpdateThread(){
		channels = new RSSService().getChannelList();
	}
}
