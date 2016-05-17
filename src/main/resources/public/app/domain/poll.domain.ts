import { PollQuestion } from './poll.question.domain.ts';

export class Poll {
    id: number;
    name: String;
    questions : PollQuestion[];
}