<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Twit extends Model
{

    public $timestamps=false;
    protected $fillable = [
        'user','twit'
    ];
}
