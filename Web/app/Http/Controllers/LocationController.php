<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class LocationController extends Controller
{
    public function cities(){
        $result =  DB::table('geozone_cities')
            ->get();
        return response()->json($result);


    }

    public function getTown(Request $request){
        $products = DB::table('geozone_city_districts')
            ->where('city_id',$request->id)
            ->select('ilce')
            ->groupBy('ilce')->get();

        return response()->json($products);
    }

    public function getDistrict(Request $request){
        $result =  DB::table('geozone_city_districts')
            ->where('city_id',$request->id)
            ->where('ilce',$request->town)
            ->get();
        return response()->json($result);
    }
}
