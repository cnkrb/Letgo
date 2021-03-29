<?php

namespace App\Http\Controllers;

use App\Favorite;
use App\Product;
use App\Yorum;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Psy\Util\Str;

class ProductController extends Controller
{
    public function addProduct(Request $request){

        if ($request->hasFile('patch')) {
            $filename_two=null;
            $filename_three=null;
            $filename_four=null;
            $filename_five=null;
            $filename_six=null;
            $filename_seven=null;
            $filename_eight=null;
            $filename_nine=null;
            $filename_ten=null;
            if ($request->hasFile('patch_two')) {
                $request->validate([
                    'patch_two' => 'image|mimes:jpg,png'
                ]);
                $filename_two = uniqid() . '.' . $request->patch_two->getClientOriginalExtension();
                $request->patch_two->move(public_path('public/products/'), $filename_two);
            }

            if ($request->hasFile('patch_three')) {
                $request->validate([
                    'patch_three' => 'image|mimes:jpg,png'
                ]);
                $filename_three = uniqid() . '.' . $request->patch_three->getClientOriginalExtension();
                $request->patch_three->move(public_path('public/products/'), $filename_three);
            }
            if ($request->hasFile('patch_four')) {
                $request->validate([
                    'patch_four' => 'image|mimes:jpg,png'
                ]);
                $filename_four = uniqid() . '.' . $request->patch_four->getClientOriginalExtension();
                $request->patch_four->move(public_path('public/products/'), $filename_four);
            }
            if ($request->hasFile('patch_five')) {
                $request->validate([
                    'patch_five' => 'image|mimes:jpg,png'
                ]);
                $filename_five = uniqid() . '.' . $request->patch_five->getClientOriginalExtension();
                $request->patch_five->move(public_path('public/products/'), $filename_five);
            }

            if ($request->hasFile('patch_six')) {
                $request->validate([
                    'patch_six' => 'image|mimes:jpg,png'
                ]);
                $filename_six = uniqid() . '.' . $request->patch_six->getClientOriginalExtension();
                $request->patch_six->move(public_path('public/products/'), $filename_six);
            }

            if ($request->hasFile('patch_seven')) {
                $request->validate([
                    'patch_seven' => 'image|mimes:jpg,png'
                ]);
                $filename_seven = uniqid() . '.' . $request->patch_seven->getClientOriginalExtension();
                $request->patch_seven->move(public_path('public/products/'), $filename_seven);
            }

            if ($request->hasFile('patch_eight')) {
                $request->validate([
                    'patch_eight' => 'image|mimes:jpg,png'
                ]);
                $filename_eight = uniqid() . '.' . $request->patch_eight->getClientOriginalExtension();
                $request->patch_eight->move(public_path('public/products/'), $filename_eight);
            }

            if ($request->hasFile('patch_nine')) {
                $request->validate([
                    'patch_nine' => 'image|mimes:jpg,png'
                ]);
                $filename_nine = uniqid() . '.' . $request->patch_nine->getClientOriginalExtension();
                $request->patch_nine->move(public_path('public/products/'), $filename_nine);
            }

            if ($request->hasFile('patch_ten')) {
                $request->validate([
                    'patch_ten' => 'image|mimes:jpg,png'
                ]);
                $filename_ten = uniqid() . '.' . $request->patch_ten->getClientOriginalExtension();
                $request->patch_ten->move(public_path('public/products/'), $filename_ten);
            }

            $request->validate([
                'patch' => 'image|mimes:jpg,png'
            ]);
            $filename = uniqid() . '.' . $request->patch->getClientOriginalExtension();
            $request->patch->move(public_path('public/products/'), $filename);
            $array  = array('salak', 'aptal');

            $degiken=trim($request->statement, '"');
            foreach($array as $ar){
                if (strpos($degiken,$ar) !== false) {
                    $degiken=str_replace($ar,'***',$degiken);
                }
            }
            $user = new Product();
            $user->product_id = trim($request->product_id, '"');
            $user->title =trim($request->title, '"');
            $user->price =trim($request->price, '"');
            $user->statement =$degiken;
            $user->location =trim($request->location, '"');
            $user->image = $filename;
            $user->category =trim($request->category, '"');
            $user->image_two = $filename_two;
            $user->image_three = $filename_three;
            $user->image_four = $filename_four;
            $user->image_five = $filename_five;
            $user->image_six = $filename_six;
            $user->image_seven = $filename_seven;
            $user->image_eight = $filename_eight;
            $user->image_nine = $filename_nine;
            $user->image_ten = $filename_ten;
            $user->save();

            return response()->json([
                'status_code' => 'c7'
            ]);
        }

        else {
            return response()->json([
                'status_code' => 'c8'
            ]);
        }
    }

    public  function  postYorum(Request $request){
        $array  = array('salak', 'aptal','mal');
        $degiken=$request->title;
        foreach($array as $ar){
            if (strpos($degiken,$ar) !== false) {
               $star='';
                for($i=1;$i<=strlen($ar);$i++) {
                    $star=$star.'*';
                }
                $degiken=str_replace($ar,$star,$degiken);
            }
        }
        $user = new Yorum();
        $user->product_id = $request->id;
        $user->title = $degiken;
        $user->save();

        return response()->json([
            'status_code' => 'c7'
        ]);
    }

    public  function  getYorum(Request $request){
        $result =  DB::table('yorums')
            ->where('product_id',$request->id)
            ->get();
        return response()->json($result);
    }

    public  function  searchLocation(Request $request){
        $result =  DB::table('products')
            ->where('location', 'LIKE', '%'.$request->location.'%')
            ->where('sell',0)
            ->get();
        return response()->json($result);
    }

    public  function  searchCategory(Request $request){
        $result =  DB::table('products')
            ->where('location', 'LIKE', '%'.$request->location.'%')
            ->where('category', $request->category)
            ->where('sell',0)
            ->get();
        return response()->json($result);
    }

    public  function  searchProduct(Request $request){

        if($request->category== 'Hepsi'){
            $result =  DB::table('products')
                ->where('location', 'LIKE', '%'.$request->location.'%')
                ->where('title', 'LIKE', '%'.$request->title.'%')
                ->where('sell',0)
                ->get();
            return response()->json($result);
        }else {
            $result =  DB::table('products')
                ->where('location', 'LIKE', '%'.$request->location.'%')
                ->where('title', 'LIKE', '%'.$request->title.'%')
                ->where('category', $request->category)
                ->where('sell',0)

                ->get();
            return response()->json($result);
        }

    }

    public  function getProduct(Request $request){
        $result =  DB::table('products')
            ->where('product_id', $request->product)
            ->where('sell',$request->sell)
            ->get();
        return response()->json($result);
    }

    public function  setFavorite(Request $request){
        $user = new Favorite();
        $user->product_id = ($request->product);
        $user->user_id =($request->user);

        $user->save();

        return response()->json([
            'status_code' => 'c10'
        ]);

    }
    public function  getFavorite(Request $request){
        $result =  DB::table('favorites')
            ->where('user_id', $request->user)
            ->get();
        //join yap
        $collection = collect();

        foreach ($result as $key){
            $resultt =  DB::table('products')
                ->where('id', $key->product_id)
                ->where('sell',0)
                ->first();
            $collection->push($resultt);

        }
        return $collection->toJson();

    }
}
