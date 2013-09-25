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
				Thread.sleep(1000*60);	//�P���ԋx�e
			}

		}
		catch(Exception e){
			throw new RuntimeException(e);
		}

	}


	private void oreBot() throws TwitterException{


		//�y��������
		Calendar cal = Calendar.getInstance();
		System.out.println("-- " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if(week == Calendar.SATURDAY || week == Calendar.SUNDAY){
			//System.out.println("  �����͓y���������x�ނ��B");
			return;
		}


		// �P���P��
		DateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
		String today = yyyymmdd.format(cal.getTime());
		if(_lastTweet != null && _lastTweet.equals(today)){
			//System.out.println("  �����͂����Ԃ₢�������I��bot�̎d���͏I�����ˁB");
			return;
		}

		// �U���܂ő҂�
		if(cal.get(Calendar.HOUR_OF_DAY)!=6){
			//System.out.println("  �܂��U���ɂȂ��ĂȂ��Ȃ��E�E�E");
			return;
		}


		//�@�����A���U���ɂȂ����B
		System.out.println("�����A�Ԃ₭���I�I");

		// Twitte4j�C���X�^���X�擾
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(false)
		  .setOAuthConsumerKey("�R���V���[�}�E�L�[")
		  .setOAuthConsumerSecret("�R���V���[�}�E�V�[�N���b�g")
		  .setOAuthAccessToken("�A�N�Z�X�E�g�[�N��")
		  .setOAuthAccessTokenSecret("�A�N�Z�X�g�[�N���E�V�[�N���b�g");
		TwitterFactory tf = new TwitterFactory(cb.build());

		Twitter tw = tf.getInstance();
		tw.updateStatus("���͂悤�������܂��B���������C�ɁE�E�E�d���ł��ˁB�����ځ[���i�L��֥`�j");

		_lastTweet = today;	//�Ԃ₢�����ۑ�

	}

	public static void main(String[] args){
		OrebotThread t = new OrebotThread();
		t.start();
	}

}
