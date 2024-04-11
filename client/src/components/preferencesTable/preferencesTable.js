import "./preferencesTable.css";
import React from 'react';
import daysMap from '../common';

function Table({terms, roomPreferences}) {
    terms = terms.sort((a, b) => a.id - b.id)

    return (
        <div className="items-center justify-center preferencesTableDiv w-3/4 h-full">
            <table className="preferencesTable">
            <tbody>
            <tr>
                <th>
                    Legenda: <br/>
                    &#10003; - głos,
                    ? - dodany komentarz
                </th>
                {terms.map(
                    (term) => {
                        return (
                            <th key={term.id}>
                            {daysMap[term.day]}
                            <br/>
                            {term.startTime}-{term.endTime}
                            </th>
                        );
                    }
                )}
                <th>Total</th>
            </tr>
            {Object.keys(roomPreferences.userPreferencesMap).map(
                (user) => {
                    let counter = 0;
                    return (
                        <tr key={user}>
                            <td>
                                {
                                    roomPreferences.users.map(u => {
                                        if (u.userId === parseInt(user)) {
                                          return (
                                            <span key={u.userId}>
                                                <div>{u.firstName} {u.lastName}{"\n"}</div>
                                                <div style={{ color: "#aaa" }}>{u.email}</div>
                                            </span>
                                          );
                                        }
                                        return null;
                                    })
                                }
                            </td>
                                {   
                                    terms.map(
                                        (term) => {
                                            let comments = roomPreferences.userPreferencesMap[user].comments;
                                            let comment_value = null;
                                            for (let comment in comments) {
                                                if (comments[comment].termId === term.id) {
                                                    comment_value = comments[comment].comment;
                                                }
                                            }

                                            if (roomPreferences.userPreferencesMap[user].selectedTerms.includes(term.id) && comment_value!=null) {
                                                counter++;
                                                return (
                                                    <td key={term.id}><strong>&#10003;</strong> + <strong>?</strong>: {comment_value}</td>
                                                );
                                            }
                                            if (roomPreferences.userPreferencesMap[user].selectedTerms.includes(term.id)) {
                                                counter++;
                                                return (
                                                    <td key={term.id}><strong>&#10003;</strong></td>
                                                );
                                            }
                                            if (comment_value!=null) {
                                                return (
                                                    <td key={term.id}><strong>?</strong>: {comment_value}</td>
                                                );
                                            }
                                            else {
                                                return (
                                                    <td key={term.id}></td>
                                                );
                                            }
                                        }
                                    )
                                }
                            <td>
                                {counter}
                            </td>
                        </tr>
                    );
                }
            )}
            </tbody>
            </table>
        </div>
    );
}

export default Table;
