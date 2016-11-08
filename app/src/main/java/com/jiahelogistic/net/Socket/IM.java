package com.jiahelogistic.net.Socket;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

/**
 * Created by Huanling on 2016/11/08 23:00.
 *
 * 即时通讯
 */

public class IM {
	private Socket mSocket = null;

	public IM() {
		try {
			mSocket = new Socket("127.0.0.1", 7610);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String start() {
		String response = "";
		try {
			OutputStream os = mSocket.getOutputStream();
			Writer writer = new OutputStreamWriter(os);
			writer.write("My Test");
			writer.flush();

			Reader reader = new InputStreamReader(mSocket.getInputStream());
			char chars[] = new char[64];
			int len;
			StringBuffer sb = new StringBuffer();
			while ((len=reader.read(chars)) != -1) {
				sb.append(new String(chars, 0, len));
			}
			response = sb.toString();

			writer.close();
			os.close();
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}
