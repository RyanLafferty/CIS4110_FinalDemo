package lafferty.com.abusivepermissions;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import java.io.*;

/**
 * Created by Lafferty on 2017-03-22.
 */

public class FileActivity extends AppCompatActivity
{
    //Activity Widget Objects
    private TextView readable = null;
    private TextView writeable = null;
    public TextView fileOutput = null;
    public EditText fileInput = null;
    private Button listRootButton = null;
    private Button createFileButton = null;
    private Button copyFileButton = null;
    private Button saveButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        //create widget objects
        readable = (TextView) findViewById(R.id.readable);
        writeable = (TextView) findViewById(R.id.writeable);
        fileOutput = (TextView) findViewById(R.id.file_output);
        fileInput = (EditText) findViewById(R.id.edit_file);
        listRootButton = (Button) findViewById(R.id.list_root);
        createFileButton = (Button) findViewById(R.id.create_file);
        copyFileButton = (Button) findViewById(R.id.copy_file);
        saveButton = (Button) findViewById(R.id.save_button);

        boolean read = isExternalStorageReadable();
        boolean write = isExternalStorageWritable();

        if (read == true)
        {
            readable.setText("External is readable");
        }
        else
        {
            readable.setText("External is NOT readable");
        }

        if (write == true)
        {
            writeable.setText("External is writeable");
        }
        else
        {
            writeable.setText("External is NOT writeable");
        }


        listRootButton.setOnClickListener(createListDirButtonListener());
        createFileButton.setOnClickListener(createFile());
        copyFileButton.setOnClickListener(copyFile());
        saveButton.setOnClickListener(saveFile());
    }

    /* Check if we can write to external storage */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Check if we can read from external storage*/
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void listRoot()
    {
        File [] files = null;
        File sdcard = Environment.getExternalStorageDirectory();
        File dirs = new File(sdcard.getAbsolutePath());
        String dirOutput = "";
        int i = 0;

        if(dirs.exists())
        {
            files = dirs.listFiles();
        }

        for(i = 0; i < files.length; i++)
        {
            dirOutput += files[i];
        }

        fileOutput.setText(dirOutput);
    }

    protected View.OnClickListener createListDirButtonListener()
    {
        return (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                File [] files = null;
                File sdcard = Environment.getExternalStorageDirectory();
                File dirs = new File(sdcard.getAbsolutePath());
                String dirOutput = "";
                int i = 0;

                if(dirs.exists())
                {
                    files = dirs.listFiles();
                }

                if(files != null)
                {
                    for(i = 0; i < files.length; i++)
                    {
                        dirOutput += files[i].getName() + "\n";
                    }

                    fileOutput.setText(dirOutput);
                }

            }
        });
    }

    protected View.OnClickListener copyFile()
    {
        return (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FileReader fr = null;
                BufferedReader br =  null;
                File [] files = null;
                File sdcard = Environment.getExternalStorageDirectory();
                File dirs = new File(sdcard.getAbsolutePath());
                //File dirs = new File("/storage/emulated/");
                String dirOutput = "";
                int i = 0;

                System.out.println("Listing contents of: " + sdcard.getAbsolutePath());

                if(dirs.exists())
                {
                    files = dirs.listFiles();
                }

                if(files != null)
                {
                    for(i = 0; i < files.length; i++)
                    {
                        //dirOutput += files[i].getName() + "\n";
                        if(files[i].getName().equals("secretFile.txt"))
                        {
                            System.out.println("Found the secret file: " + files[i].getName());
                            String filename = "copiedFile.txt";
                            String line = "";

                            //copy the file
                            try
                            {
                                //Open the files
                                Context context = getBaseContext();
                                PrintWriter outputStream = new PrintWriter(context.getFilesDir() + "/" + filename);
                                
                                System.out.println("Reading from: " + sdcard.getAbsolutePath()
                                        + "/" + files[i].getName());
                                System.out.println("Writing to: " + context.getFilesDir() + "/" + filename);

                                FileReader inputStream = new FileReader(sdcard.getAbsolutePath() + "/" + files[i].getName());
                                BufferedReader  r = new BufferedReader(inputStream);

                                //Read one file into the copied file
                                while(line != null)
                                {
                                    line = r.readLine();
                                    if(line != null)
                                    {
                                        outputStream.println(line);
                                    }
                                }

                                //close files
                                r.close();
                                inputStream.close();
                                outputStream.close();
                                r = null;
                                inputStream = null;
                                outputStream = null;

                                fileOutput.setText("File Successfully Copied");
                            }
                            catch (Exception e)
                            {
                                System.out.println("Error: Could not copy file");
                            }
                        }
                    }

                }

            }
        });
    }

    protected View.OnClickListener createFile()
    {
        return (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PrintWriter pr = null;
                File [] files = null;
                File sdcard = Environment.getExternalStorageDirectory();
                File createdFile = new File(sdcard.getAbsolutePath() + "/secretFile.txt");

                try
                {
                    if(createdFile.exists())
                    {
                        createdFile.delete();
                        createdFile = null;
                        createdFile = new File(sdcard.getAbsolutePath() + "/secretFile.txt");
                    }
                    pr = new PrintWriter(createdFile);
                    pr.println("!MALICOUS CODE!");
                    pr.close();
                    pr = null;
                }
                catch(Exception e)
                {
                    System.out.println("Error: Could not create file");
                }

            }
        });
    }

    protected View.OnClickListener saveFile()
    {
        return (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PrintWriter pr = null;
                File [] files = null;
                File sdcard = Environment.getExternalStorageDirectory();
                File createdFile = new File(sdcard.getAbsolutePath() + "/secretFile.txt");

                try
                {
                    if(createdFile.exists())
                    {
                        createdFile.delete();
                        createdFile = null;
                        createdFile = new File(sdcard.getAbsolutePath() + "/secretFile.txt");
                    }
                    pr = new PrintWriter(createdFile);
                    pr.println(fileInput.getText().toString());
                    //pr.println("!MALICOUS CODE!");
                    pr.close();
                    pr = null;
                }
                catch(Exception e)
                {
                    System.out.println("Error: Could not create file");
                }

            }
        });
    }
}
