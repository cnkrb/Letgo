<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Product extends Model
{
    public $timestamps=false;

    protected $fillable = [
        'sell','product_id', 'category','title', 'image','image_two','price','location','statement','image_two','image_three'
        ,'image_four','image_five','image_six','image_seven','image_eight','image_nine','image_ten'
    ];



}
