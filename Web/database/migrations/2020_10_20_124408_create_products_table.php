<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateProductsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('products', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('product_id');
            $table->string('title');
            $table->string('category');
            $table->Integer('sell')->default(0)->nullable();
            $table->string('price');
            $table->String('location');
            $table->string('statement');
            $table->string('image');
            $table->string('image_two')->nullable();
            $table->string('image_three')->nullable();
            $table->string('image_four')->nullable();
            $table->string('image_five')->nullable();
            $table->string('image_six')->nullable();
            $table->string('image_seven')->nullable();
            $table->string('image_eight')->nullable();
            $table->string('image_nine')->nullable();
            $table->string('image_ten')->nullable();
        });
    }


    public function down()
    {
        Schema::dropIfExists('products');
    }
}
