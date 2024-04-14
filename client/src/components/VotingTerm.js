import { useState, useEffect } from 'react';
import { Card, CardDescription } from '../ui/card';

const VotingTerm = ({
    term,
    minHour,
    termWithIdIsSelected, 
    termWithIdComments
}) => {
    const startTime = term.startTime;
    const endTime = term.endTime;
    const startHour = startTime.getHours();
    const endHour = endTime.getHours();
    const endMinute = endTime.getMinutes();
    const startMinute = startTime.getMinutes();

    const hour = term.startTime.getHours();
    let minute = term.startTime.getMinutes();
    if (minute < 15) {
        minute = 0;
    } else if (minute < 30) {
        minute = 1;
    } else if (minute < 45) {
        minute = 2;
    } else if (minute < 60) {
        minute = 3;
    }

    const duration = 6;
    const column = term.day + 3;

    const [color, setColor] = useState('bg-white');
    const [showCommentTextField, setShowCommentTextField] = useState(false);

    useEffect(() => {
        if (!termWithIdIsSelected) return;
        if (!termWithIdComments) return;
        
        if (termWithIdIsSelected.current.get(term.id.toString())) {
            setColor('bg-emerald-300');
        } 

        if (termWithIdComments.current.get(term.id.toString()) !== "" 
            && termWithIdComments.current.get(term.id.toString()) !== null) {
            setShowCommentTextField(true);
        } else {
            setShowCommentTextField(false);
        }
    }, [termWithIdIsSelected, termWithIdComments, term.id]);

    const handlePinComment = (e) => {
        e.stopPropagation(); 
        setShowCommentTextField(true);
    };

    const handleUnpinComment = (e) => {
        e.stopPropagation(); 
        setShowCommentTextField(false);
        if (termWithIdComments.current) {
            termWithIdComments.current.set(term.id.toString(), "");
        }
    };

    const handleTextareaClick = (e) => {
        e.stopPropagation(); 
    };
    
    const handleTabClick = () => {
        if (!termWithIdIsSelected.current.get(term.id.toString())) {
            setColor('bg-emerald-300'); 
            termWithIdIsSelected.current.set(term.id.toString(), true);
        } else {
            setColor('bg-white');
            termWithIdIsSelected.current.set(term.id.toString(), false);
        }
    };

    const handleTextareaChange = (e) => {
        const comment = e.target.value;
        if (termWithIdComments.current) {
            termWithIdComments.current.set(term.id.toString(), comment);
        }
    };

    return (
        <>
            <div
                className={`z-[2] mx-4 my-1 ${color}`} 
                style={{
                    gridRow: 1 + (hour - minHour) * 4 + minute,
                    gridRowEnd: 1 + (hour - minHour) * 4 + minute + duration,
                    gridColumn: column,
                }}
                onClick={handleTabClick} 
            >
                <Card
                    className={`flex h-full w-full flex-col items-center justify-center rounded font-medium z-2`}
                >
                    <CardDescription className={`flex justify-between cursor-default`}>
                        <span className="">{startHour}:{startMinute === 0 ? "00" : startMinute}</span>
                        <span className="text-md">-</span>
                        <span className="">{endHour}:{endMinute === 0 ? "00" : endMinute}</span>
                    </CardDescription>
                    {showCommentTextField ? (
                        <>
                            <textarea
                                className="w-full h-24 p-2 mt-2 rounded border border-gray-300 focus:outline-none focus:border-blue-500 text-xs"
                                placeholder="Your comment..."
                                defaultValue={termWithIdComments.current.get(term.id.toString())}
                                onClick={handleTextareaClick} 
                                onChange={handleTextareaChange} 
                            />
                            <button className="mt-2 bg-red-500 text-white px-3 py-1 rounded" onClick={handleUnpinComment}>Usuń komentarz</button>
                        </>
                    ) : (
                        <button className="mt-2 bg-green-500 text-white px-3 py-1 rounded" onClick={handlePinComment}>Przypnij komentarz</button>
                    )}
                </Card>
            </div>
        </>
    );
};

export default VotingTerm;
