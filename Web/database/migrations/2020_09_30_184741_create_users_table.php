<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('product_id');
            $table->string('name');
            $table->string('surname');
            $table->string('image')->default('Resim seçiniz')->nullable();
            $table->string('biography')->default('Biografi yazınız')->nullable();
            $table->string('location')->nullable();
            $table->string('password');
            $table->string('username');
            $table->string('phone_number')->default('Numaranızı yazınız')->nullable();
            $table->string('mail')->default('Mail adresi yazınız')->nullable();
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('users');
    }
}
