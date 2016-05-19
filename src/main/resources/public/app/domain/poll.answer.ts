import { PollQuestion } from './poll.question.domain.ts';
import { Poll } from './poll.domain.ts';

export class PollAnswer {
    id: number;
    user: String;
    poll : Poll;
    pollQuestion: PollQuestion; //the question that was selected
}