package com.iammaksimus.recipes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements IMain{


    private ListView recipesListView;
    public static MainPresenter mainPresenter;
    private Context context;
    private EditText search;
    private LinearLayout recipeDay;
    private ImageView cover;
    private TextView nameRecipeDay;
    private ImageButton close;
    private InterstitialAd mInterstitialAd;
    private Button recipeDayTap;
    AdView mAdView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        context = v.getContext();


     /*   AdView adView = (AdView)v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
*/

        recipesListView = (ListView)v.findViewById(R.id.recipesList);
        search = (EditText)v.findViewById(R.id.search);
        mainPresenter = new MainPresenter(this, search);
        cover = (ImageView)v.findViewById(R.id.coverRecipeDay);
        recipeDay = (LinearLayout)v.findViewById(R.id.recipeDay);
        nameRecipeDay = (TextView) v.findViewById(R.id.nameRecipeDay);
        close = (ImageButton)v.findViewById(R.id.closeRecipeDay);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeDay.setVisibility(View.GONE);
            }
        });
        recipeDayTap = (Button)v.findViewById(R.id.recipeDayTap);
        recipeDayTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.progressBar.setVisibility(View.VISIBLE);
                Values.context = context;
                Recipe r = new Recipe(Values.recipeDay.getName(), "", Values.recipeDay.getIngredients(), "", Values.recipeDay.getCover(), Values.recipeDay.getUrl());
                Values.recipe = r;
                Values.ingredients = Values.recipeDay.getIngredients();
                new ParserRecipes().execute(Values.recipeDay.getUrl());
            }
        });
        Values.recipeDayL = recipeDay;
        Values.coverRecipeDay = cover;
        Values.nameRecipeDay = nameRecipeDay;
        if(!Values.day) {
            new RecipeDayParser().execute();
            Values.day = true;
        }
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public ListView getRecipesList() {
        return recipesListView;
    }

    @Override
    public Context getContext() {
        return context;
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
        void onFragmentInteraction(Uri uri);
    }
}
