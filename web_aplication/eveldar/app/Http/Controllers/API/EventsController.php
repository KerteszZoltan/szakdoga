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
            ['end', '>', date('Y-m-d H:i:sa')],
            ['user_id', '=', $id]
        ])->orderByRaw('end ASC')->get();

        return [
            'events'=>$active
        ];
    }

    public function complete(Request $request){
        $user=auth()->user();
        $id=$user['id'];

        $complete=Event::where([
            ['complete', '=', '1'],
            ['user_id', '=', $id]
        ])->orderByRaw('end ASC')->get();

        return [
            'events'=>$complete
        ];
    }

    public function expire(Request $request){
        $user=auth()->user();
        $id=$user['id'];

        $expired=Event::where([
            ['complete', '=', '0'],
            ['end', '<', date('Y-m-d H:i:sa')],
            ['user_id', '=', $id]
        ])->orderByRaw('end ASC')->get();

        return [
            'events'=>$expired
        ];
    }

    public function specified(Request $request){
        $user=auth()->user();
        $user_id=$user['id'];
        $event_id=$request['event_id'];

        $event=Event::where([
            ['id', '=', $event_id],
            ['user_id', '=', $user_id]
        ])->first();

        if ($event!=null) {
            $response = [
                'events'=>$event
            ];
        }else{
            $response = [
                'Hibás adat'
            ];
        }
        return response($response);
    }

    public function update(Request $request){
        $id=$request['event_id'];
        $user=auth()->user();
        $user_id=$user['id'];
        $event=Event::find($id);

        if ($event['user_id']!=$user_id) {
            return response()->json([
                'message'=>'Elerhetetlen adat!'
            ]);
        }
        if(isset($request['complete'])){
            if($request['complete'] == 0){
                $event->complete = '0';
                $event->save();
            }
            if($request['complete']==1){
                $event->complete='1';
                $event->save();
            }
        }
        $event->update($request->all());

        return [
            'events'=>$event
        ];
    }

    public function delete(Request $request){
        $id=$request['event_id'];
        $user=auth()->user();
        $user_id=$user['id'];
        $event=Event::find($id);
        if ($event['user_id'] != $user_id) {
            return response()->json([
                'message'=>'Sikertelen torles!'
            ]);
        }

        Event::destroy($id);

        return [
            'message'=>'Sikeres torles'
        ];
    }

    public function store(Request $request){
        if ($request['topic'] == '' || $request['start'] == '' || $request['end'] == '' ) {
            return response()->json([
                'message'=>'A cim, kezdesi, befejezesi datum kotelezo'
            ]);
        }

        $event =$request->user()->events()->create([
            'topic' => $request->topic,
            'description' => $request->description,
            'start'=>$request->start,
            'end'=>$request->end,
        ]);

        return $event;
    }
}
