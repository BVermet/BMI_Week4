package be.howest.nmct.bmi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Main extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText uwLengte;
    private EditText uwGewicht;
    private TextView uwCategory;
    private TextView uwIndex;
    private Button uwUpdate;
    private ImageView uwImage;
    BMIInfo bmi = new BMIInfo();
    public static final String PREFS_NAME = "MyPrefsFile";


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Main.
     */
    // TODO: Rename and change types and number of parameters
    public static Main newInstance(String param1, String param2) {
        Main fragment = new Main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Main() {
        // Required empty public constructor
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(getClass().getSimpleName(),"onResumeFrag");
        toonBMI();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getClass().getSimpleName(),"onStopFrag");
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        try {
            editor.putInt("weight", Integer.parseInt(uwGewicht.getText().toString()));
            editor.putFloat("lengte", Float.parseFloat(uwLengte.getText().toString()));
        }
        catch(Exception e){
            editor.putInt("weight",0);
            editor.putInt("lengte",0);
        }
        editor.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main,container,false);

        this.uwLengte = (EditText) v.findViewById(R.id.txtHeight);
        this.uwGewicht = (EditText) v.findViewById(R.id.txtMass);
        this.uwIndex = (TextView)v.findViewById(R.id.txtIndex);
        this.uwCategory = (TextView)v.findViewById(R.id.txtCategory);
        this.uwImage = (ImageView) v.findViewById(R.id.imgBMI);
        this.uwUpdate = (Button) v.findViewById(R.id.btnUpdate);


        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        int gewicht = settings.getInt("weight", 0);
        float lengte = settings.getFloat("lengte", 0f);
        if(gewicht != 0 && lengte != 0){
            uwGewicht.setText(String.valueOf(gewicht));
            uwLengte.setText(String.valueOf(lengte));
        }

        this.uwUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                toonBMI(v);
            }
        });
        return v;
    }

    public void toonBMI(View v){

        float height = Float.parseFloat(this.uwLengte.getText().toString());
        int gewicht = Integer.parseInt(this.uwGewicht.getText().toString());

        bmi.setHeight(height);
        bmi.setMass(gewicht);

        Category c = bmi.returnCategory();
        System.out.println(c);


       uwIndex.setText(""+ bmi.CalculateBMI(height,gewicht));
       uwCategory.setText(c.toString());
       uwImage.setImageResource( getResourceId(c));
    }

    public void toonBMI(){
        float lengte;
        int controlegewicht;
        try{
            lengte = Float.parseFloat(uwLengte.getText().toString());
            System.out.println(Float.parseFloat(uwLengte.getText().toString()));
            controlegewicht = Integer.parseInt(uwGewicht.getText().toString());
            System.out.println(Integer.parseInt(uwGewicht.getText().toString()));
        }catch(Exception e){
            lengte = -1f;
            controlegewicht = -1;
        }


        System.out.println(lengte);
        System.out.println(controlegewicht);
        if(lengte != -1f && controlegewicht != -1) {


            float height = Float.parseFloat(this.uwLengte.getText().toString());
            int gewicht = Integer.parseInt(this.uwGewicht.getText().toString());

            bmi.setHeight(height);
            bmi.setMass(gewicht);

            Category c = bmi.returnCategory();
            System.out.println(c);


            uwIndex.setText("" + bmi.CalculateBMI(height, gewicht));
            uwCategory.setText(c.toString());
            uwImage.setImageResource(getResourceId(c));
        }
    }


    private int getResourceId(Category cat){
        switch(cat){
            case GROOT_ONDERGEWICHT:
                return R.drawable.silhouette_1;
            case ERNSTIG_ONDERGEWICHT:
                return R.drawable.silhouette_2;
            case ONDERGEWICHT:
                return R.drawable.silhouette_3;
            case NORMAAL:
                return R.drawable.silhouette_4;
            case OVERGEWICHT:
                return R.drawable.silhouette_5;
            case MATIG_OVERGEWICHT:
                return R.drawable.silhouette_6;
            case ERNSTIG_OVERGEWICHT:
                return R.drawable.silhouette_7;
            case ZEER_GROOT_OVERGEWICHT:
                return R.drawable.silhouette_8;
            default:
                return R.drawable.silhouette_1;
        }

        //Testen Toast
        //Toast.makeText(getActivity(),"Uw BMI bedraagt:", Toast.LENGTH_LONG).show();

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        toonBMI();
        Log.d(getClass().getSimpleName(),"onAttachFrag");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
