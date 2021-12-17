<?php

namespace App\Http\Controllers;

use App\Models\Event;
use Illuminate\Auth\Events\Login;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Str;


class EventController extends Controller
{

    public function __construct(){
        $this->middleware(['auth']);
    }

    public function index(){
        return view('events.create_event');
    }

    public function active_events(){
        $events = DB::table('events')
                                    ->where('user_id', auth()->user()->id)
                                    ->where('start', '>', date('Y-m-d H:i:sa') )
                                    ->orderByRaw('start DESC')
                                    ->paginate(2);

        return view('events.active_event',[
            'events' => $events
        ]);
    }

    public function store(Request $request){
        $this->validate($request, [
            'start'=> 'required | date',
            'end'=> 'required | date',
            'topic'=> 'required',
        ]);

        $request->user()->events()->create([
            'topic' => $request->topic,
            'description' => $request->description,
            'start'=>$request->start,
            'end'=>$request->end,
        ]);

        return redirect()->route('active_events');
    }

    public function id_event($id){
        $event = Event::find($id);
        return view('events.modify_event',[
            'event' => $event
        ]);
    }

    public function update(Request $request,$id){
        $this-> validate($request,[
            'topic'=> "required",
            'description'=> "required",
            'start' => "required | date",
            'end' => "required | date",
        ]);


        $event = Event::find($id);
        $event->topic = $request->input('topic');
        $event->description = $request->input('description');
        $event->start = $request->input('start');
        $event->end = $request->input('end');
        $event->user_id = auth()->user()->id;
        $event->save();
        return back();
    }
}
