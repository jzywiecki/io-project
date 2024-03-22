export const Div=({className, children, ...props})=>(<div className={"lg:w-1/3 md:w-1/2 w-3/4 mb-3 shadow appearance-none border rounded py-3 px-3 text-gray-700 leading-tight "+className} {...props}>
        {children}
    </div>)