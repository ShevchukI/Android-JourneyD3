package com.peryite.journeyd3.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peryite.journeyd3.R;
import com.peryite.journeyd3.tools.LogTag;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCredits.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCredits#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCredits extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    private final static String WEBSITE_JOURNEY = "https://d3resource.com/journey/";
    private final static String BAGNSTONE_PROFILE_REDDIT = "https://www.reddit.com/user/bagstone";

    private View view;

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentCredits() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCredits.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCredits newInstance(String param1, String param2) {
        FragmentCredits fragment = new FragmentCredits();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(LogTag.INFORMATION, "Fragment Arguments not null");
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_credits, container, false);
        view.findViewById(R.id.website).setOnClickListener(click -> {
            Log.d(LogTag.CLICK, "Click on " + WEBSITE_JOURNEY);
            goToUrl(WEBSITE_JOURNEY);
            Log.d(LogTag.CLICK, "Open " + WEBSITE_JOURNEY);
        });
        view.findViewById(R.id.bastone_profile).setOnClickListener(click -> {
            Log.d(LogTag.CLICK, "Click on " + BAGNSTONE_PROFILE_REDDIT);
            goToUrl(BAGNSTONE_PROFILE_REDDIT);
            Log.d(LogTag.CLICK, "Open " + BAGNSTONE_PROFILE_REDDIT);
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void goToUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
