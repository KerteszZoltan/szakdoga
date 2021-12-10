<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Auth\RegisterController;
use App\Http\Controllers\Auth\LoginController;
use App\Http\Controllers\Auth\LogoutController;
use App\Http\Controllers\ProfileController;
use App\Http\Controllers\EventController;



/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('about');
});

Route::get('/about', function(){
    return view('about');
})->name('about');

Route::get('/register', function(){
    return view('auth.register');
})->name('register');
Route::get('/register', [RegisterController::class, 'index'])->name('register');
Route::post('/register', [RegisterController::class, 'store']);

Route::get('/login', [LoginController::class, 'index'])->name('login');
Route::post('/login', [LoginController::class, 'store']);

Route::post('/logout', [LogoutController::class, 'store'])->name('logout');


Route::get('/profile', [ProfileController::class, 'index'])->name('profile');

Route::get('/new_event', [EventController::class, 'index'])->name('new_event');
Route::post('/new_event', [EventController::class, 'store']);

Route::get('/active_event', [EventController::class, 'active_events'])->name('active_events');

Route::post('/event-{id}', [EventController::class, 'id_event'])->name('modify_event');



