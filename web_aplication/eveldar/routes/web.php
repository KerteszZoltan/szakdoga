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

Route::get('/ASZF', function ()
{
    return view('aszf');
})->name('aszf');

Route::get('/register', function(){
    return view('auth.register');
})->name('register');
Route::get('/register', [RegisterController::class, 'index'])->name('register');
Route::post('/register', [RegisterController::class, 'store']);

Route::get('/login', [LoginController::class, 'index'])->name('login');
Route::post('/login', [LoginController::class, 'store']);

Route::post('/logout', [LogoutController::class, 'store'])->name('logout');


Route::get('/profile', [ProfileController::class, 'index'])->name('profile');
Route::get('/modify_profile', [ProfileController::class, 'modify_index'])->name('modify_profile');
Route::post('/modify_profile', [ProfileController::class, 'update']);
Route::get('/modify_password', [ProfileController::class, 'update_index'])->name('password_update');
Route::post('/modify_password', [ProfileController::class, 'password_update']);


Route::get('/delete_profile', [ProfileController::class, 'confirm_delete'])->name('delete_profil');
Route::post('/delete_profile', [ProfileController::class, 'delete']);


Route::get('/new_event', [EventController::class, 'index'])->name('new_event');
Route::post('/new_event', [EventController::class, 'store']);

Route::get('/complete_event', [EventController::class, 'complete_list'])->name('complete_list');

Route::get('/expired_event', [EventController::class, 'expired_list'])->name('expired_event');
Route::get('/active_event', [EventController::class, 'active_events'])->name('active_events');


Route::get('/event-{id}', [EventController::class, 'id_event'])->name('modify_event');
Route::post('/event-{id}', [EventController::class, 'update']);

Route::post('/delete_event/{id}', [EventController::class, 'delete'])->name('delete');
Route::post('/complete_event/{id}', [EventController::class, 'complete_update'])->name('complete');



