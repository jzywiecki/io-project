export const Div=({className, children, ...props})=>(<div className={"w-3/4 mb-3 shadow appearance-none border rounded py-3 px-3 text-gray-700 leading-tight "+className} {...props}>
        {children}
    </div>)