<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});



Route::post('/addUser','AccountController@store');
Route::post('/setPicture','AccountController@da');
Route::post('/loginUser','AccountController@login');
Route::post('/uploadImage','AccountController@da');
Route::post('/getProfile','AccountController@getProfile');
Route::post('/updateProfile','AccountController@updateProfile');

Route::post('/addProduct','ProductController@addProduct');

Route::post('/searchLocation','ProductController@searchLocation');
Route::post('/searchCategory','ProductController@searchCategory');
Route::post('/searchProduct','ProductController@searchProduct');
Route::post('/getProduct','ProductController@getProduct');
Route::post('/setFavorite','ProductController@setFavorite');
Route::post('/getFavorite','ProductController@getFavorite');

Route::get('/getCities','LocationController@cities');
Route::post('/getTown','LocationController@getTown');
Route::post('/getDistrict','LocationController@getDistrict');


