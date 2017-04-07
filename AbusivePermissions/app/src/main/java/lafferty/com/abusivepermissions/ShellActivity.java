package lafferty.com.abusivepermissions;

import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Process;

public class ShellActivity extends AppCompatActivity
{
    private Button exeButton = null;
    private Button monkeyButton = null;
    private Button rmButton = null;
    private Button lsButton = null;

    /*commands that work
    * netstat - show connections & streams
    * cal - output calendar
    * ps - show processes
    * vmstat - show jvm statistics
    * sleep - will sleep the phone
    * service list - list running services
    * rm - remove file
    * rmdir - remove directory
    * cp - copy file
    * mv - move file
    * cat - output contents of a file
    * touch - create file
    * monkey - create file (on some devices)
    * toybox - has a bunch of packaged system utilities
    * toybox help -a -> dump command list with usages
    * toybox nc -> netcat, can forge headers and create http requets with a lot of fiddling
    *              (probably better to just prompt the user for internet permission, they'll click yes anyways)
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);

        final TextView shellOut = (TextView) findViewById(R.id.shell_output);
        final EditText shellInput = (EditText) findViewById(R.id.shell_input);
        exeButton = (Button) findViewById(R.id.execute_button);
        monkeyButton = (Button) findViewById(R.id.jni_create_file);
        rmButton = (Button) findViewById(R.id.jni_rm);
        lsButton = (Button) findViewById(R.id.jni_ls);
        shellOut.setMovementMethod(new ScrollingMovementMethod());

        exeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //System.out.println(shellInput.getText().toString());
                //System.out.println(lsCall(shellInput.getText().toString()));
                //shellOut.setText(lsCall(shellInput.getText().toString()));
                //

                //System.out.println(dCall());

                try
                {
                    String cmd [] = {"ls", "-a", "/storage/emulated/0"};
                    String cmd2 [] = {"monkey", "/storage/emulated/0/Music/test.txt"};
                    String cmd3 [] = {"ls", "-a", "/system/bin"};
                    String userCMD [] = shellInput.getText().toString().split(" ");
                    String line = "";
                    String output = "";
                    Process p = Runtime.getRuntime().exec(userCMD);
                    p.waitFor();
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while(line != null)
                    {
                        line = r.readLine();
                        if(line != null)
                        {
                            System.out.println(line);
                            output += line + "\n";
                        }
                    }
                    shellOut.setText(output);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                //System.out.println(curlCall());
                //suCall();
                //shellOut.setText(suTestCall());
            }
        });



        monkeyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shellOut.setText(cCall());
            }
        });

        lsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shellOut.setText(lsCall(shellInput.getText().toString()));
            }
        });

        rmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shellOut.setText(dCall());
            }
        });

        //mainOut.setText(lsCall());
        //mainOut.setMovementMethod(new ScrollingMovementMethod());
    }


    //native libraries
    public native String lsCall(String input);
    public native String cCall();
    public native String curlCall();
    public native String dCall();
    public native String upCall();
    public native String webCall();
    public native String suCall();
    public native String suTestCall();

    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-lib");
    }
}
