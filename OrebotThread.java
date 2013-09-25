package jp.arcanum.orebot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class OrebotThread extends Thread{

	private String _lastTweet;

	@Override
	public void run() {

		try{

			while(true){

				oreBot();
				Thread.sleep(1000*60);	//１分間休憩
			}

		}
		catch(Exception e){
			throw new RuntimeException(e);
		}

	}


	private void oreBot() throws TwitterException{


		//土日を除く
		Calendar cal = Calendar.getInstance();
		System.out.println("-- " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if(week == Calendar.SATURDAY || week == Calendar.SUNDAY){
			//System.out.println("  今日は土日だから休むよ。");
			return;
		}


		// １日１回
		DateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
		String today = yyyymmdd.format(cal.getTime());
		if(_lastTweet != null && _lastTweet.equals(today)){
			//System.out.println("  今日はもうつぶやいたからオレbotの仕事は終わりね。");
			return;
		}

		// ６時まで待つ
		if(cal.get(Calendar.HOUR_OF_DAY)!=13){
			//System.out.println("  まだ６時になってないなぁ・・・");
			return;
		}


		//　さぁ、朝６時になった。
		System.out.println("さぁ、つぶやくぞ！！");

		// Twitte4jインスタンス取得
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(false)
		  .setOAuthConsumerKey("コンシューマ・キー")
		  .setOAuthConsumerSecret("コンシューマ・シークレット")
		  .setOAuthAccessToken("アクセス・トークン")
		  .setOAuthAccessTokenSecret("アクセストークン・シークレット");
		TwitterFactory tf = new TwitterFactory(cb.build());

		Twitter tw = tf.getInstance();
		tw.updateStatus("おはようございます。今日も元気に・・・仕事ですね。しょぼーん（´･ω･`）");

		_lastTweet = today;	//つぶやいた日保存

	}

	public static void main(String[] args){
		OrebotThread t = new OrebotThread();
		t.start();
	}

}
