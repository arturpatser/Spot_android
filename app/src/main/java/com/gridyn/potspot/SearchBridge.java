package com.gridyn.potspot;

import com.gridyn.potspot.response.SpotSearchResponse;

import java.util.List;

public class SearchBridge {

    private static List<SpotSearchResponse.Spots> mSpots;

    public static void setDataForSearchResult(List<SpotSearchResponse.Spots> data) {
        mSpots = data;
    }

    public static List<SpotSearchResponse.Spots> getDataForSearchResult() {
        return mSpots;
    }
}
