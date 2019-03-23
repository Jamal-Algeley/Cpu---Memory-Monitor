

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {
    TextView cpuTemp,cpuInfo ,totalcpuusage,battery_health,ava_mem,usage_mem,battery_capacity,battery_voltage;
    TextView cpu_label,memory_label,percent,cellecus,usage_label,used_label,available_label,voltage_label,
            capacity_label,health_label,battery_label,GB_label1,GB_label2,mV,mAh,temp_label;


    Typeface font;
    private Context mContext;
    Activity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        activity= Home.this;

        cpuTemp = (TextView)findViewById(R.id.cpuTemp);
        cpuInfo = (TextView)findViewById(R.id.cpuInfo);
        totalcpuusage = (TextView)findViewById(R.id.totalcpuusage);
        ava_mem= (TextView)findViewById(R.id.ava_mem);
        usage_mem= (TextView)findViewById(R.id.usage_mem);
        battery_voltage=(TextView)findViewById(R.id.battery_voltage);
        battery_capacity=(TextView)findViewById(R.id.battery_capacity);
        cellecus=(TextView)findViewById(R.id.cellecus);
        percent=(TextView)findViewById(R.id.percent);
        memory_label=(TextView)findViewById(R.id.memory_label);
        cpu_label=(TextView)findViewById(R.id.cpu_label);
        usage_label=(TextView)findViewById(R.id.usage_label);
        temp_label=(TextView)findViewById(R.id.temp_label);
        battery_health = (TextView) findViewById(R.id.battery_health);
        used_label=(TextView)findViewById(R.id.used_label);
        available_label=(TextView)findViewById(R.id.available_label);
        voltage_label=(TextView)findViewById(R.id.voltage_label);
        capacity_label=(TextView)findViewById(R.id.capacity_label);
        health_label=(TextView)findViewById(R.id.health_label);
        battery_label=(TextView)findViewById(R.id.battery_label);
        GB_label1=(TextView)findViewById(R.id.GB_label1);
        GB_label2=(TextView)findViewById(R.id.GB_label2);
        mV=(TextView)findViewById(R.id.mV);
        mAh=(TextView)findViewById(R.id.mAh);

        font = Typeface.createFromAsset(activity.getAssets(), "rockwell.TTF");

        cpuTemp.setTypeface(font);
        totalcpuusage.setTypeface(font);
        cpu_label.setTypeface(font);
        memory_label.setTypeface(font);
        percent.setTypeface(font);
        cellecus.setTypeface(font);
        usage_label.setTypeface(font);
        temp_label.setTypeface(font);

        ava_mem.setTypeface(font);
        usage_mem.setTypeface(font);
        battery_voltage.setTypeface(font);
        battery_capacity.setTypeface(font);
        battery_health.setTypeface(font);

        used_label.setTypeface(font);
        available_label.setTypeface(font);
        voltage_label.setTypeface(font);
        capacity_label.setTypeface(font);
        health_label.setTypeface(font);
        battery_label.setTypeface(font);
        GB_label1.setTypeface(font);
        GB_label2.setTypeface(font);
        mV.setTypeface(font);
        mAh.setTypeface(font);




        //------------------------------------------------------
        // cpu Temp , finctio


        // cpu usage every half sec secinds

        cpuTemp.setText(new DecimalFormat("##.#").format(getCpuTemp())  +"");

        final Handler handler0 = new Handler();
        final int delay0 = 500; //milliseconds
        handler0.postDelayed(new Runnable(){
            public void run(){



                cpuTemp.setText(new DecimalFormat("##.#").format(getCpuTemp())  +"");

                handler0.postDelayed(this, delay0);
            }
        }, delay0);
        //-------------------------------------------------------


        // cpu info 1
        try {

           // cpuInfo.setText("cpu info:     "+getCPUInfo());

            for (Map.Entry<String,String> entry : getCPUInfo().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                cpuInfo.append(key+":  "+value+"\n");
            }

        }catch(IOException e){

        }

        //---

        Log.i("TAG", "SERIAL: " + Build.SERIAL);
        Log.i("TAG","MODEL: " + Build.MODEL);
        Log.i("TAG","ID: " + Build.ID);
        Log.i("TAG","Manufacture: " + Build.MANUFACTURER);
        Log.i("TAG","brand: " + Build.BRAND);
        Log.i("TAG","type: " + Build.TYPE);
        Log.i("TAG","user: " + Build.USER);
        Log.i("TAG","BASE: " + Build.VERSION_CODES.BASE);
        Log.i("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.i("TAG","SDK API " + Build.VERSION.SDK);
        Log.i("TAG","BOARD: " + Build.BOARD);
        Log.i("TAG","BRAND " + Build.BRAND);
        Log.i("TAG","HOST " + Build.HOST);
        Log.i("TAG","FINGERPRINT: "+Build.FINGERPRINT);
        Log.i("TAG","android Version Code: " + Build.VERSION.RELEASE);
        //Log.i("TAG","SECURITY_PATCH: " + Build.VERSION.SECURITY_PATCH);
        Log.i("TAG","PRODUCT: " + Build.PRODUCT);
        Log.i("TAG","DEVICE: " + Build.DEVICE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = fields[Build.VERSION.SDK_INT + 1].getName();
        Log.i("TAG","Android OsName: " + osName);


        //------------------------------




        // cpu usage every half sec secinds


        getCoresUsage();

        final Handler handler = new Handler();
        final int delay = 500; //milliseconds
        handler.postDelayed(new Runnable(){
            public void run(){

                getCoresUsage();

                handler.postDelayed(this, delay);
            }
        }, delay);


        //---------------------------------------
        //battery heakth

        mContext = getApplicationContext();

        // Initialize a new IntentFilter instance
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        // Register the broadcast receiver
        mContext.registerReceiver(mBroadcastReceiver,iFilter);

        // Get the widgets reference from XML layout



        //-------------

        // memamry

        getMemInfo();

        final Handler handler2 = new Handler();
        final int delay2 = 500; //milliseconds
        handler2.postDelayed(new Runnable(){
            public void run(){

                getMemInfo();

                handler2.postDelayed(this, delay2);
            }
        }, delay2);

      //------------------------battery_capacity

       // Log.i("getBatteryCapacity","" + getBatteryCapacity(this)+" mAh");
        battery_capacity.setText(getBatteryCapacity(this)+"");

        //---------------------------battery_voltage

        IntentFilter intentfilter;
        intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Home.this.registerReceiver(broadcastreceiver,intentfilter);




    }//on create


    //---------------------------------


    // get evry single core usage
   public void getCoresUsage (){

       String[] fruits = new String[] {

       };
      // ExpandableHeightGridView cpuUsage;

     /*  cpuUsage = (ExpandableHeightGridView)findViewById(R.id.cpuUsage);

       final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));
       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
               (this, R.layout.cpu_item, fruits_list);
       cpuUsage.setAdapter(arrayAdapter);*/

       float[] coreValues = new float[10];
       float totalUsagew= 0;

      // arrayAdapter.clear();


       for(byte i = 0; i < getNumberOfCores(); i++)
       {
           coreValues[i] = readCore(i);

           //fruits_list.add("Cpu"+i+": "+ new DecimalFormat("##.#").format(coreValues[i]*100) +"%");
          // arrayAdapter.notifyDataSetChanged();
           float x=coreValues[i];
           if (Float.isNaN(x)) // when one of cores gives you Nan(not a number) it means the core is not active
               x=0.0f;
           totalUsagew=totalUsagew+x*100;
       }

       totalcpuusage.setText(new DecimalFormat("##.#").format(totalUsagew=totalUsagew/ getNumberOfCores())  + "");



   }
//-------------------------------------------

    public double getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return batteryCapacity;

    }


//


    private BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int batteryVol = (int)(intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0));
            //float fullVoltage = (float) (batteryVol * 0.001);

            battery_voltage.setText(batteryVol+"");



        }
    };

    //------------------

    private void getMemInfo() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);

        Runtime runtime = Runtime.getRuntime();



       String x= new DecimalFormat("##.#").format ((((memoryInfo.availMem/1024.0)/1024.0)/1024.0) );
        String y= new DecimalFormat("##.#").format(((((memoryInfo.totalMem/1024.0)/1024.0)/1024.0)) );

        ava_mem.setText(x+"");
        
        float used = Float.parseFloat(y)-Float.parseFloat(x);

        usage_mem.setText(new DecimalFormat("##.#").format(used)  + "");

        //total_mem.setText("Total Memory"+y+ "GB");
    }

    //----
    //for multi core value
    private float readCore(int i)
    {
        /*
         * how to calculate multicore
         * this function reads the bytes from a logging file in the android system (/proc/stat for cpu values)
         * then puts the line into a string
         * then spilts up each individual part into an array
         * then(since he know which part represents what) we are able to determine each cpu total and work
         * then combine it together to get a single float for overall cpu usage
         */
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            //skip to the line we need
            for(int ii = 0; ii < i + 1; ++ii)
            {
                reader.readLine();
            }
            String load = reader.readLine();

            //cores will eventually go offline, and if it does, then it is at 0% because it is not being
            //used. so we need to do check if the line we got contains cpu, if not, then this core = 0
            if(load.contains("cpu"))
            {
                String[] toks = load.split(" ");

                //we are recording the work being used by the user and system(work) and the total info
                //of cpu stuff (total)
                //https://stackoverflow.com/questions/3017162/how-to-get-total-cpu-usage-in-linux-c/3017438#3017438

                long work1 = Long.parseLong(toks[1])+ Long.parseLong(toks[2]) + Long.parseLong(toks[3]);
                long total1 = Long.parseLong(toks[1])+ Long.parseLong(toks[2]) + Long.parseLong(toks[3]) +
                        Long.parseLong(toks[4]) + Long.parseLong(toks[5])
                        + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

                try
                {
                    //short sleep time = less accurate. But android devices typically don't have more than
                    //4 cores, and I'n my app, I run this all in a second. So, I need it a bit shorter
                    Thread.sleep(200);
                }
                catch (Exception e) {}

                reader.seek(0);
                //skip to the line we need
                for(int ii = 0; ii < i + 1; ++ii)
                {
                    reader.readLine();
                }
                load = reader.readLine();
                //cores will eventually go offline, and if it does, then it is at 0% because it is not being
                //used. so we need to do check if the line we got contains cpu, if not, then this core = 0%
                if(load.contains("cpu"))
                {
                    reader.close();
                    toks = load.split(" ");

                    long work2 = Long.parseLong(toks[1])+ Long.parseLong(toks[2]) + Long.parseLong(toks[3]);
                    long total2 = Long.parseLong(toks[1])+ Long.parseLong(toks[2]) + Long.parseLong(toks[3]) +
                            Long.parseLong(toks[4]) + Long.parseLong(toks[5])
                            + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);



                    //here we find the change in user work and total info, and divide by one another to get our total
                    //seems to be accurate need to test on quad core
                    //https://stackoverflow.com/questions/3017162/how-to-get-total-cpu-usage-in-linux-c/3017438#3017438

                    return (float)(work2 - work1) / ((total2 - total1));
                }
                else
                {
                    reader.close();
                    return 0;
                }

            }
            else
            {
                reader.close();
                return 0;
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return 0;
    }


    float getCpuTemp() {

        //sds
            /*

        "/sys/devices/system/cpu/cpu0/cpufreq/cpu_temp"
    "/sys/devices/system/cpu/cpu0/cpufreq/FakeShmoo_cpu_temp"
    "/sys/class/thermal/thermal_zone1/temp"
    "/sys/class/i2c-adapter/i2c-4/4-004c/temperature"
    "/sys/devices/platform/tegra-i2c.3/i2c-4/4-004c/temperature"
    "/sys/devices/platform/omap/omap_temp_sensor.0/temperature"
    "/sys/devices/platform/tegra_tmon/temp1_input"
    "/sys/kernel/debug/tegra_thermal/temp_tj"
    "/sys/devices/platform/s5p-tmu/temperature"
    "/sys/class/thermal/thermal_zone0/temp"
    "/sys/devices/virtual/thermal/thermal_zone0/temp"
    "/sys/class/hwmon/hwmon0/device/temp1_input"
    "/sys/devices/virtual/thermal/thermal_zone1/temp"
    "/sys/devices/platform/s5p-tmu/curr_temp"


         */
        // sdsd


        Process p;
        try {
            p = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone0/temp");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = reader.readLine();
            if(Float.parseFloat(line)>2000)
                // لان الرقم اللي يجيب فيه من جهازالهواوي كبير ، مش عارف شن عناها أو بأي وحدة قياس ، لكن المهم ضروري نقسموها على1000 لو كبير
            { float temp = Float.parseFloat(line) / 1000.0f;
                return temp;
            }
            else

            return Float.parseFloat(line);

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }


    public static Map<String, String> getCPUInfo () throws IOException {

        BufferedReader br = new BufferedReader (new FileReader("/proc/cpuinfo"));

        String str;

        Map<String, String> output = new HashMap<>();

        while ((str = br.readLine ()) != null) {

            String[] data = str.split (":");

            if (data.length > 1) {

                String key = data[0].trim ().replace (" ", "_");
                if (key.equals ("model_name")) key = "cpu_model";

                String value = data[1].trim ();

                if (key.equals ("cpu_model"))
                    value = value.replaceAll ("\\s+", " ");

                output.put (key, value);

            }

        }

        br.close ();

        return output;

    }



    private int getNumberOfCores() {

            return Runtime.getRuntime().availableProcessors();

    }


    public void getMemoryInfo() {
        try {
            Process proc = Runtime.getRuntime().exec("cat /proc/meminfo");
            InputStream is = proc.getInputStream();
            TextView textView = (TextView)findViewById(R.id.cpuInfo);
            textView.setText(getStringFromInputStream(is));
        }
        catch (IOException e) {
            Log.e("", "------ getMemoryInfo " + e.getMessage());
        }
    }

    private static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        try {
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (IOException e) {
            Log.e("", "------ getStringFromInputStream " + e.getMessage());
        }
        finally {
            if(br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    Log.e("", "------ getStringFromInputStream " + e.getMessage());
                }
            }
        }

        return sb.toString();
    }




    //--------------------------------------------------------------
    // Initialize a new BroadcastReceiver instance
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(mContext, "Received", Toast.LENGTH_SHORT).show();

            /*
                BatteryManager
                    The BatteryManager class contains strings and constants used for values in the
                    ACTION_BATTERY_CHANGED Intent, and provides a method for querying battery
                    and charging properties.
            */
            /*
                public static final String EXTRA_HEALTH
                    Extra for ACTION_BATTERY_CHANGED: integer containing the current
                    health constant.

                    Constant Value: "health"
            */

            // Get the battery health indicator integer value
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

            // Initialize a new String to hold battery health
            String healthString = "";

            // Determine the battery health from return integer value
            if(health == BatteryManager.BATTERY_HEALTH_COLD){
                healthString = "COLD";
            }else if (health == BatteryManager.BATTERY_HEALTH_DEAD){
                healthString = "DEAD";
            }else if (health == BatteryManager.BATTERY_HEALTH_GOOD){
                healthString = "GOOD";
            }else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                healthString = "OVER HEAT";
            }else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                healthString = "OVER VOLTAGE";
            }else if(health == BatteryManager.BATTERY_HEALTH_UNKNOWN){
                healthString = "UNKNOWN";
            }else if(health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                healthString = "UNSPECIFIED FAILURE";
            }

            // Display the battery voltage in TextView
            battery_health.setText(healthString);
        }
    };




}