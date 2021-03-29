<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFavoritesTable extends Migration
{

    public function up()
    {
        Schema::create('favorites', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('product_id');
            $table->string('user_id');
            $table->timestamps();
        });
    }


    public function down()
    {
        Schema::dropIfExists('favorites');
    }
}
