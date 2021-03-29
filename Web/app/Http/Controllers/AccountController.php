<?php

namespace App\Http\Controllers;

use App\Game;
use App\Product;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class AccountController extends Controller
{

    public function index()
    {

    }
    public function getProfile(Request $request)
    {
        $user = DB::table('users')->where('username', '=', $request->username)->first();
        return response()->json($user);
    }


    public function create()
    {
    }

    public function updateProfile(Request  $request){

        $user = User::find($request->id);
        $user->name =$request->name;
        $user->surname =$request->surname;
        $user->biography =$request->biography;
        $user->password =$request->password;
        $user->username =$request->username;
        $user->phone_number =$request->phone_number;
        $user->mail =$request->mail;
        $user->update();

        return response()->json([
            'status_code' => 'cenk'
        ]);
    }



    public function da(Request $request)
    {
        if ($request->hasFile('patch')) {
            $request->validate([
                'patch' => 'image|mimes:jpg,png'
            ]);
            $filename = uniqid() . '.' . $request->patch->getClientOriginalExtension();
            $request->patch->move(public_path('public/profile/'), $filename);


            $user = User::find($request->id);
            $user->image =$filename;
            $user->update();
            return response()->json([
                'status_code' => 'cenk'
            ]);
        }
        return response()->json([
            'status_code' => 'değer yok'
        ]);
    }

    public function login(Request $request)
    {
        $user = DB::table('users')->where('username', '=', $request->username)->exists();
        if (!$user) {
            $userr = DB::table('users')->where('mail', '=', $request->username)->exists();
            if (!$userr) {
                    return response()->json([
                        'status_code' => 'C1'
                    ]);
                } else {
                $user = DB::table('users')->where('mail', '=', $request->username)->first();
                if ($user->password  == $request->password) {
                    return response()->json([
                        'status_code' => 'C3'
                    ]);
                } else {
                    return response()->json([
                        'status_code' => 'C2'
                    ]);
                }
                }
        } else {
            $user = DB::table('users')->where('username', '=', $request->username)->first();
                if ($user->password  == $request->password) {
                    return response()->json([
                        'status_code' => 'C3'
                    ]);
                } else {
                    return response()->json([
                        'status_code' => 'C2'
                    ]);
                }

        }
    }

    public function store(Request $request)
    {

        $user = DB::table('users')->where('mail', '=', $request->mail)->exists();
        if ($user) {
            return response()->json([
                'status_code' => 'C4'
            ]);
        } else {
            $user = DB::table('users')->where('username', '=', $request->username)->exists();
            if ($user) {
                return response()->json([
                    'status_code' => 'C6'
                ]);
            } else {
                $unicode = $this->generateRandomString();

                $user = new User();
                $user->name = $request->name;
                $user->surname = $request->surname;
                $user->username = $request->username;
                $user->password = $request->password;
                $user->product_id = $unicode;
                $user->image ='user.png';
                $user->mail = $request->mail;
                $user->save();


                return response()->json([
                    'status_code' => 'C5'
                ]);
            }


        }
    }


    public function generateRandomString($length = 20)
    {
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        return $randomString;
    }

    public function show($id)
    {
        //
    }


    public function edit($id)
    {
        //
    }

    public function update(Request $request, $id)
    {
        //
    }


    public function destroy($id)
    {
        //
    }

    public function uploadImage(Request $request)
    {

        $this->validate($request, [
            'file' => 'required|image|mimes:jpeg,png,jpg|max:2048',
        ]);


        if ($request->hasFile('file')) {
            $image = $request->file('file');
            $name = $image->getClientOriginalName();
            $destinationPath = public_path('/images');
            $image->move($destinationPath, $name);

            $userImage = new UserImage();
            $userImage->name = $name;
            $userImage->save();
        }

        return response()->json(['success' => 'Image uploaded successfully']);

    }
}
