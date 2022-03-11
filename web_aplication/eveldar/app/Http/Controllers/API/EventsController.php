<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\User;
use App\Models\Event;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Auth;

class EventsController extends Controller
{
    public function active(Request $request){
        $user=auth()->user();
        $id=$user['id'];

        $active=Event::where([
            ['complete', '=', '0'],
            ['end', '<', date('Y-m-d H:i:sa')],
            ['user_id', '=', $id]
        ])->get();

        return response($active);
    }

    public function complete(Request $request){
        $user=auth()->user();
        $id=$user['id'];

        $complete=Event::where([
            ['complete', '=', '1'],
            ['user_id', '=', $id]
        ])->get();

        return response($complete);
    }

    public function expire(Request $request){
        $user=auth()->user();
        $id=$user['id'];

        $expired=Event::where([
            ['complete', '=', '0'],
            ['end', '>', date('Y-m-d H:i:sa')],
            ['user_id', '=', $id]
        ])->get();

        return response($expired);
    }
}
