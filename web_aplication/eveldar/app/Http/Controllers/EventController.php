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
        //Str::substr($event->start, -3);
        //Str::substr($event->end, -3);
        //\dd($event);
        return view('events.modify_event',[
            'event' => $event
        ]);
    }

    public function update(Request $request){
        $this-> validate($request,[
            'topic'=> "required",
            'start' => "required | date",
            'end' => "required | date",
        ]);

        $request->user()->event()->update([
            'topic' => $request->topic,
            'description' => $request->description,
            'start'=>$request->start,
            'end'=>$request->end,
        ]);
    }
}
