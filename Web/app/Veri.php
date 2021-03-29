<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Veri extends Model
{
    public $timestamps=false;
    protected $fillable = [
        'user,twit'
    ];
}
