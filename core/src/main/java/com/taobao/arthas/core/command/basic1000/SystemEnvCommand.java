package com.taobao.arthas.core.command.basic1000;

import static com.taobao.text.ui.Element.label;

import java.util.Map;
import java.util.Map.Entry;

import com.taobao.arthas.core.command.Constants;
import com.taobao.arthas.core.command.result.PropertyResult;
import com.taobao.arthas.core.shell.cli.Completion;
import com.taobao.arthas.core.shell.cli.CompletionUtils;
import com.taobao.arthas.core.shell.command.AnnotatedCommand;
import com.taobao.arthas.core.shell.command.CommandProcess;
import com.taobao.arthas.core.util.StringUtils;
import com.taobao.middleware.cli.annotations.Argument;
import com.taobao.middleware.cli.annotations.Description;
import com.taobao.middleware.cli.annotations.Name;
import com.taobao.middleware.cli.annotations.Summary;
import com.taobao.text.Decoration;
import com.taobao.text.ui.TableElement;
import com.taobao.text.util.RenderUtil;

/**
 * @author hengyunabc 2018-11-09
 *
 */
@Name("sysenv")
@Summary("Display the system env.")
@Description(Constants.EXAMPLE + "  sysenv\n" + "  sysenv USER\n" + Constants.WIKI + Constants.WIKI_HOME + "sysenv")
public class SystemEnvCommand extends AnnotatedCommand {

    private String envName;

    @Argument(index = 0, argName = "env-name", required = false)
    @Description("env name")
    public void setOptionName(String envName) {
        this.envName = envName;
    }

    @Override
    public void process(CommandProcess process) {
        try {
            PropertyResult result = new PropertyResult();
            if (StringUtils.isBlank(envName)) {
                // show all system env
                result.putAll(System.getenv());
            } else {
                // view the specified system env
                String value = System.getenv(envName);
                result.put(envName, value);
            }
            process.appendResult(result);
        } finally {
            process.end();
        }
    }

    /**
     * First, try to complete with the sysenv command scope. If completion is
     * failed, delegates to super class.
     *
     * @param completion
     *            the completion object
     */
    @Override
    public void complete(Completion completion) {
        CompletionUtils.complete(completion, System.getenv().keySet());
    }

}
