import { Navigate } from "react-router-dom";

export const getDayNumber=(day)=> {
    switch (day) {
        case "MONDAY": return 0;
        case "TUESDAY": return 1;
        case "WEDNESDAY": return 2;
        case "THURSDAY": return 3;
        case "FRIDAY": return 4;
        case "SATURDAY": return 5;
        case "SUNDAY": return 6;
        default: return 0;
    }
}

export const getTermFromDto=(termDto)=> {
    var startTime = new Date();
    var endTime = new Date();

    startTime.setHours(termDto.startTime[0]);
    startTime.setMinutes(termDto.startTime[1]);
    endTime.setHours(termDto.endTime[0]);
    endTime.setMinutes(termDto.endTime[1]);

    return {
        id: termDto.id,
        day: getDayNumber(termDto.day),
        startTime: startTime,
        endTime: endTime,
    };
} 


export const prepareUserPreferences=(termWithIdIsSelected, termWithIdComments)=> {
    let dataBackendFormat = {
        selectedTerms: [],
        comments: []
    }

    for (const [key, _] of termWithIdIsSelected.entries()) {
        if (termWithIdIsSelected.get(key)) {
            const parsedKey = parseInt(key, 10);
            dataBackendFormat.selectedTerms.push(parsedKey);
        }
    }

    for (const [key, value] of termWithIdComments.entries()) {
        if (termWithIdComments.get(key) === "") {
            continue;
        }
        dataBackendFormat.comments.push({termId: key, comment: value}); 
    }

    return dataBackendFormat;
}

export const checkAfterResponse=(error)=>{
    Navigate("/login");
}