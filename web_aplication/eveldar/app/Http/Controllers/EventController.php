<?php

namespace App\Http\Controllers;

use App\Mail\NewEventAdded;
use App\Mail\NewRegisteredUser;
use App\Models\Event;
use Illuminate\Auth\Events\Login;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;
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
        $events = Event::where('user_id', auth()->user()->id)
                         ->where('end', '>', date('Y-m-d H:i:sa') )
                         ->where('complete', '=', '0')
                         ->orderByRaw('end ASC')
                         ->paginate(2);

        return view('events.event',[
            'events' => $events,
            'type'=>'active',
        ]);
    }

    public function complete_list(){
        $events = Event::where('user_id', auth()->user()->id)
                         ->where('complete', '=', '1')
                         ->orderByRaw('updated_at DESC')
                         ->paginate(2);

        return view('events.event',[
            'events' => $events,
            'type'=>'complete',
        ]);
    }

    public function expired_list()
    {
        $events = Event::where('user_id', auth()->user()->id)
                         ->where('end', '<', date('Y-m-d H:i:sa') )
                         ->where('complete', '=', '0')
                         ->orderByRaw('end ASC')
                         ->paginate(2);

        return view('events.event',[
            'events' => $events,
            'type'=>'expired',
        ]);
    }

    public function store(Request $request){
        $this->validate($request, [
            'start'=> 'required | date | after_or_equal:today',
            'end'=> 'required | date | after:start',
            'topic'=> 'required',
        ]);

        $request->user()->events()->create([
            'topic' => $request->topic,
            'description' => $request->description,
            'start'=>$request->start,
            'end'=>$request->end,
        ]);

        $user=auth()->user();
        $event=Event::where('user_id', '=', auth()->user()->id)
        ->latest('created_at')->first();

        Mail::to($user)->send(new NewEventAdded($event,$user));
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
            'start'=> 'required | date | after_or_equal:today',
            'end'=> 'required | date | after:start',
        ]);


        $event = Event::find($id);
        $event->topic = $request->input('topic');
        $event->description = $request->input('description');
        $event->start = $request->input('start');
        $event->end = $request->input('end');
        $event->user_id = auth()->user()->id;
        $event->save();
        return redirect()->route('active_events');
    }

    public function delete($id)
    {
        $event=Event::find($id);
        print $id;
        $event=Event::find($id)->delete('id','=',$event);
        return \back();
    }

    public function complete_update($id)
    {
        print $id;
        $event=Event::find($id);
        $event->complete = '1';
        $event->user_id = auth()->user()->id;
        $event->save();
        return back();
    }
}
