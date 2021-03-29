<?php

namespace App;

use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;

class User extends Authenticatable
{
    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */

    //         $table->string('product_id');
    //            $table->string('name');
    //            $table->string('surname');
    //            $table->string('image')->default('Resim seçiniz')->nullable();
    //            $table->string('biography')->default('Biografi yazınız')->nullable();
    //            $table->string('location')->nullable();
    //            $table->string('password');
    //            $table->string('username');
    //            $table->string('phone_number')->default('Numaranızı yazınız')->nullable();
    //            $table->string('mail')
    protected $fillable = [
        'name', 'surname', 'image','biography','location','password','username','phone_number','mail','product_id',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    /**
     * The attributes that should be cast to native types.
     *
     * @var array
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];


}
