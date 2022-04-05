class NewEventAdded extends Mailable
{
    use Queueable, SerializesModels;
    public $event;
    public $user;
    public function __construct(Event $event, User $user)
    {
        $this->event=$event;
        $this->user=$user;
    }
    public function build()
    {
        return $this->markdown('mails.events.new_events')
        ->subject('Uj esemeny kerult hozzaadasra!');
    }
}