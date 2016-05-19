import { PollChoice } from './poll.choice.domain.ts';

export class Poll {
    id: number;
    name: String;
    choices : PollChoice[];
}