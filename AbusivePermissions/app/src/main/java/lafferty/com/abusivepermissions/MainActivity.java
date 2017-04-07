package lafferty.com.abusivepermissions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mainOut = (TextView) findViewById(R.id.main_output);
        Button fileButton = (Button) findViewById(R.id.file_button);
        Button shellButton = (Button) findViewById(R.id.shell_button);

        fileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(intent);
            }
        });

        shellButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ShellActivity.class);
                startActivity(intent);
            }
        });

    // Example of a call to a native method
    //TextView tv = (TextView) findViewById(R.id.sample_text);
    //tv.setText(stringFromJNI());
        //System.out.println(lsCall());


        //mainOut.setText(lsCall());
        //mainOut.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    //public native String lsCall();

    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-lib");
    }
}
