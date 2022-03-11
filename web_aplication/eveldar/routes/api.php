<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\API\UserController;
use App\Http\Controllers\API\EventsController;


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

Route::post('/login', [UserController::class, 'login']);

Route::post('/logout', [UserController::class, 'logout'])->middleware('auth:sanctum');
Route::get('/profile', [UserController::class, 'profile'])->middleware('auth:sanctum');

Route::get('/active', [EventsController::class, 'active'])->middleware('auth:sanctum');
Route::get('/complete', [EventsController::class, 'complete'])->middleware('auth:sanctum');
Route::get('/expired', [EventsController::class, 'expire'])->middleware('auth:sanctum');




