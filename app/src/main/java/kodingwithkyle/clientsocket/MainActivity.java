package kodingwithkyle.clientsocket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private static String mIpAddress = "10.0.0.109";
    private static Socket mSocket;
    private static ServerSocket mServerSocket;
    private static InputStreamReader mStreamReader;
    private static BufferedReader mBufferedReader;
    private static PrintWriter mPrintWriter;

    private String mMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.et);
    }

    public void sendText(View view) {
        mMessage = editText.getText().toString();

        MyTask myTask = new MyTask();
        myTask.execute();
        Toast.makeText(getApplicationContext(), "Data sent", Toast.LENGTH_SHORT).show();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mSocket = new Socket(mIpAddress, 5000); //connect to the socket @ port 5000
                mPrintWriter = new PrintWriter(mSocket.getOutputStream()); //get the output stream
                mPrintWriter.write(mMessage); //send message through the socket
                mPrintWriter.flush();
                mPrintWriter.close();
                mSocket.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
